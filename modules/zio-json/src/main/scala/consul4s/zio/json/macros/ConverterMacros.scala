package consul4s.zio.json.macros

import zio.json.JsonCodec

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object ConverterMacros {
  def derive[Repr, Target]: JsonCodec[Target] = macro deriveImpl[Repr, Target]

  private val specialCapitalizeFieldNames =
    Map(
      "grpc" -> "GRPC",
      "grpcusetls" -> "GRPCUseTLS",
      "areaid" -> "AreaID",
      "serviceid" -> "ServiceID",
      "checkid" -> "CheckID",
      "kv" -> "KV",
      "ttl" -> "TTL",
      "id" -> "ID",
      "tcp" -> "TCP",
      "http" -> "HTTP",
      "tlsskipverify" -> "TLSSkipVerify"
    )

  private val specialDecapitalizeFieldNames =
    Map(
      "GRPC" -> "grpc",
      "GRPCUSETLS" -> "grpcUseTLS",
      "AREAID" -> "areaId",
      "SERVICEID" -> "serviceId",
      "CHECKID" -> "checkId",
      "KV" -> "kv",
      "TTL" -> "ttl",
      "ID" -> "id",
      "TCP" -> "tcp",
      "HTTP" -> "http",
      "TLSSKIPVERIFY" -> "tlsSkipVerify"
    )

  private def isCaseClass(c: blackbox.Context)(symbol: c.universe.Symbol): Unit =
    if (!symbol.isClass || !symbol.asClass.isCaseClass) {
      c.abort(c.enclosingPosition, s"${symbol.fullName} must be a case class")
    }

  private def fields(c: blackbox.Context)(tpe: c.universe.Type): Seq[c.universe.Symbol] = {
    import c.universe._

    tpe.decls.collectFirst {
      case s: MethodSymbol if s.isPrimaryConstructor => s
    }.get.paramLists.head
  }

  private def capitalize(str: String): String =
    specialCapitalizeFieldNames.getOrElse(str.toLowerCase, str.capitalize)

  private def decapitalize(str: String): String =
    specialDecapitalizeFieldNames.getOrElse(str.toUpperCase, str.charAt(0).toLower + str.substring(1))

  def deriveImpl[Repr: c.WeakTypeTag, Target: c.WeakTypeTag](c: blackbox.Context): c.Expr[JsonCodec[Target]] = {
    import c.universe._

    val repr = weakTypeTag[Repr].tpe
    val target = weakTypeTag[Target].tpe

    isCaseClass(c)(repr.typeSymbol)
    isCaseClass(c)(target.typeSymbol)

    val reprFields = fields(c)(repr)
    val targetFields = fields(c)(target)

    val reprFieldMap = reprFields.map { field =>
      val name = field.name.toTermName
      val mapKey = TermName(decapitalize(name.decodedName.toString))

      q"$mapKey = t.$name"
    }

    val targetFieldMap = targetFields.map { field =>
      val name = field.name.toTermName
      val mapKey = TermName(capitalize(name.decodedName.toString))

      q"$mapKey = t.$name"
    }

    val reprCompanion = repr.typeSymbol.companion
    val targetCompanion = target.typeSymbol.companion

    c.Expr[JsonCodec[Target]](
      q"""
          import _root_.zio.json.DeriveJsonCodec
          
          val converter = new Converter[$repr, $target] {
           def from(t: $repr): $target = $targetCompanion(..$reprFieldMap)

           def to(t: $target): $repr = $reprCompanion(..$targetFieldMap)
       }
       
       DeriveJsonCodec.gen[$repr].xmap(
        repr => converter.from(repr),
        inst => converter.to(inst)
       )
       """
    )
  }
}
