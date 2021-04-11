package consul4s.zio.json.macros

import consul4s.BaseSpec

class ConverterMacrosSpec extends BaseSpec {
  case class Test(id: Int, node: String, ttl: Long)
  case class TestRepr(ID: Int, Node: String, TTL: Long)

  "ConverterMacros" should {
    "derive Converter[TestRepr, Test]" in {
      val codec = ConverterMacros.derive[TestRepr, Test]

      val inst = Test(1, "2", 3)
      val json = codec.encoder.encodeJson(inst, None)

      json.toString shouldBe """{"ID":1,"Node":"2","TTL":3}"""
      codec.decoder.decodeJson(json) shouldBe Right(inst)
    }
  }
}
