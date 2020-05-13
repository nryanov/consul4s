package consul4s.circe.model

import consul4s.model.agent._
import consul4s.model.catalog.ServiceAddress
import consul4s.model.health.{HealthCheck, HealthCheckDefinition}
import consul4s.model.{ServiceKind, Status}
import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe._

trait Agent { this: Catalog with Health with Common =>
  implicit val agentAuthorizeDecoder: Decoder[AgentAuthorize] = new Decoder[AgentAuthorize] {
    override def apply(c: HCursor): Result[AgentAuthorize] = for {
      authorized <- c.downField("Authorized").as[Boolean]
      reason <- c.downField("Reason").as[String]
    } yield AgentAuthorize(authorized, reason)
  }

  implicit val agentAuthorizeParamsDecoder: Decoder[AgentAuthorizeParams] = new Decoder[AgentAuthorizeParams] {
    override def apply(c: HCursor): Result[AgentAuthorizeParams] = for {
      target <- c.downField("Target").as[String]
      clientCertUri <- c.downField("ClientCertUri").as[String]
      clientCertSerial <- c.downField("ClientCertSerial").as[String]
    } yield AgentAuthorizeParams(target, clientCertUri, clientCertSerial)
  }

  implicit val agentCheckDecoder: Decoder[AgentCheck] = new Decoder[AgentCheck] {
    override def apply(c: HCursor): Result[AgentCheck] = for {
      node <- c.downField("Node").as[String]
      checkID <- c.downField("CheckID").as[String]
      name <- c.downField("Name").as[String]
      status <- c.downField("Status").as[Status]
      notes <- c.downField("Notes").as[String]
      output <- c.downField("Output").as[String]
      serviceID <- c.downField("ServiceID").as[String]
      serviceName <- c.downField("ServiceName").as[String]
      tType <- c.downField("Type").as[String]
      definition <- c.downField("Definition").as[HealthCheckDefinition]
      namespace <- c.downField("Namespace").as[Option[String]]
    } yield AgentCheck(
      node,
      checkID,
      name,
      status,
      notes,
      output,
      serviceID,
      serviceName,
      tType,
      definition,
      namespace
    )
  }

  implicit val agentCheckRegistrationDecoder: Decoder[AgentCheckRegistration] = new Decoder[AgentCheckRegistration] {
    override def apply(c: HCursor): Result[AgentCheckRegistration] = for {
      id <- c.downField("ID").as[Option[String]]
      name <- c.downField("Name").as[String]
      serviceId <- c.downField("ServiceId").as[Option[String]]
      checkID <- c.downField("CheckID").as[Option[String]]
      args <- c.downField("Args").as[Option[List[String]]]
      dockerContainerID <- c.downField("DockerContainerID").as[Option[String]]
      shell <- c.downField("Shell").as[Option[String]]
      interval <- c.downField("Interval").as[Option[String]]
      timeout <- c.downField("Timeout").as[Option[String]]
      ttl <- c.downField("TTL").as[Option[String]]
      http <- c.downField("HTTP").as[Option[String]]
      header <- c.downField("Header").as[Option[Map[String, String]]]
      method <- c.downField("Method").as[Option[String]]
      body <- c.downField("Body").as[Option[String]]
      tcp <- c.downField("TCP").as[Option[String]]
      status <- c.downField("Status").as[Option[Status]]
      notes <- c.downField("Notes").as[Option[String]]
      tlsSkipVerify <- c.downField("TLSSkipVerify").as[Option[Boolean]]
      gRPC <- c.downField("GRPC").as[Option[String]]
      gRPCUseTLS <- c.downField("GRPCUseTLS").as[Option[Boolean]]
      aliasNode <- c.downField("AliasNode").as[Option[String]]
      aliasService <- c.downField("AliasService").as[Option[String]]
      deregisterCriticalServiceAfter <- c.downField("DeregisterCriticalServiceAfter").as[Option[String]]
    } yield AgentCheckRegistration(
      id,
      name,
      serviceId,
      checkID,
      args,
      dockerContainerID,
      shell,
      interval,
      timeout,
      ttl,
      http,
      header,
      method,
      body,
      tcp,
      status,
      notes,
      tlsSkipVerify,
      gRPC,
      gRPCUseTLS,
      aliasNode,
      aliasService,
      deregisterCriticalServiceAfter
    )
  }

  implicit val agentCheckRegistrationEncoder: Encoder[AgentCheckRegistration] = new Encoder[AgentCheckRegistration] {
    override def apply(a: AgentCheckRegistration): Json = Json.obj(
      ("ID", a.id.asJson),
      ("Name", a.name.asJson),
      ("ServiceId", a.serviceId.asJson),
      ("CheckID", a.checkID.asJson),
      ("Args", a.args.asJson),
      ("DockerContainerID", a.dockerContainerID.asJson),
      ("Shell", a.shell.asJson),
      ("Interval", a.interval.asJson),
      ("Timeout", a.timeout.asJson),
      ("TTL", a.ttl.asJson),
      ("HTTP", a.http.asJson),
      ("Header", a.header.asJson),
      ("Method", a.method.asJson),
      ("Body", a.body.asJson),
      ("TCP", a.tcp.asJson),
      ("Status", a.status.asJson),
      ("Notes", a.notes.asJson),
      ("TLSSkipVerify", a.tlsSkipVerify.asJson),
      ("GRPC", a.gRPC.asJson),
      ("GRPCUseTLS", a.gRPCUseTLS.asJson),
      ("AliasNode", a.aliasNode.asJson),
      ("AliasService", a.aliasService.asJson),
      ("DeregisterCriticalServiceAfter", a.deregisterCriticalServiceAfter.asJson)
    )
  }

  implicit val agentMemberDecoder: Decoder[AgentMember] = new Decoder[AgentMember] {
    override def apply(c: HCursor): Result[AgentMember] = for {
      name <- c.downField("Name").as[String]
      addr <- c.downField("Addr").as[String]
      port <- c.downField("Port").as[Int]
      tags <- c.downField("Tags").as[Map[String, String]]
      status <- c.downField("Status").as[Int]
      protocolMin <- c.downField("ProtocolMin").as[Int]
      protocolMax <- c.downField("ProtocolMax").as[Int]
      protocolCur <- c.downField("ProtocolCur").as[Int]
      delegateMin <- c.downField("DelegateMin").as[Int]
      delegateMax <- c.downField("DelegateMax").as[Int]
      delegateCur <- c.downField("DelegateCur").as[Int]
    } yield AgentMember(
      name,
      addr,
      port,
      tags,
      status,
      protocolMin,
      protocolMax,
      protocolCur,
      delegateMin,
      delegateMax,
      delegateCur
    )
  }

  implicit val agentServiceDecoder: Decoder[AgentService] = new Decoder[AgentService] {
    override def apply(c: HCursor): Result[AgentService] = for {
      kind <- c.downField("Kind").as[Option[ServiceKind]]
      id <- c.downField("ID").as[String]
      service <- c.downField("Service").as[String]
      tags <- c.downField("Tags").as[List[String]]
      meta <- c.downField("Meta").as[Map[String, String]]
      port <- c.downField("Port").as[Int]
      address <- c.downField("Address").as[String]
      taggedAddresses <- c.downField("TaggedAddresses").as[Option[Map[String, ServiceAddress]]]
      weights <- c.downField("Weights").as[AgentWeights]
      enableTagOverride <- c.downField("EnableTagOverride").as[Boolean]
      createIndex <- c.downField("CreateIndex").as[Option[Long]]
      modifyIndex <- c.downField("ModifyIndex").as[Option[Long]]
      contentHash <- c.downField("ContentHash").as[Option[String]]
      proxy <- c.downField("Proxy").as[Option[AgentServiceConnectProxyConfig]]
      connect <- c.downField("Connect").as[Option[AgentServiceConnect]]
    } yield AgentService(
      kind,
      id,
      service,
      tags,
      meta,
      port,
      address,
      taggedAddresses,
      weights,
      enableTagOverride,
      createIndex,
      modifyIndex,
      contentHash,
      proxy,
      connect
    )
  }

  implicit val agentServiceEncoder: Encoder[AgentService] = new Encoder[AgentService] {
    override def apply(a: AgentService): Json = Json.obj(
      ("Kind", a.kind.asJson),
      ("ID", a.id.asJson),
      ("Service", a.service.asJson),
      ("Tags", a.tags.asJson),
      ("Meta", a.meta.asJson),
      ("Port", a.port.asJson),
      ("Address", a.address.asJson),
      ("TaggedAddresses", a.taggedAddresses.asJson),
      ("Weights", a.weights.asJson),
      ("EnableTagOverride", a.enableTagOverride.asJson),
      ("CreateIndex", a.createIndex.asJson),
      ("ModifyIndex", a.modifyIndex.asJson),
      ("ContentHash", a.contentHash.asJson),
      ("Proxy", a.proxy.asJson),
      ("Connect", a.connect.asJson)
    )
  }

  implicit val agentServiceCheckDecoder: Decoder[AgentServiceCheck] = new Decoder[AgentServiceCheck] {
    override def apply(c: HCursor): Result[AgentServiceCheck] = for {
      checkID <- c.downField("CheckID").as[Option[String]]
      name <- c.downField("Name").as[Option[String]]
      args <- c.downField("ScriptArgs").as[Option[List[String]]]
      dockerContainerID <- c.downField("DockerContainerID").as[Option[String]]
      shell <- c.downField("Shell").as[Option[String]]
      interval <- c.downField("Interval").as[Option[String]]
      timeout <- c.downField("Timeout").as[Option[String]]
      ttl <- c.downField("TTL").as[Option[String]]
      http <- c.downField("HTTP").as[Option[String]]
      header <- c.downField("Header").as[Option[Map[String, String]]]
      method <- c.downField("Method").as[Option[String]]
      body <- c.downField("Body").as[Option[String]]
      tcp <- c.downField("TCP").as[Option[String]]
      status <- c.downField("Status").as[Option[Status]]
      notes <- c.downField("Notes").as[Option[String]]
      tlsSkipVerify <- c.downField("TLSSkipVerify").as[Option[Boolean]]
      gRPC <- c.downField("GRPC").as[Option[String]]
      gRPCUseTLS <- c.downField("GRPCUseTLS").as[Option[String]]
      aliasNode <- c.downField("AliasNode").as[Option[String]]
      aliasService <- c.downField("AliasService").as[Option[String]]
      deregisterCriticalServiceAfter <- c.downField("DeregisterCriticalServiceAfter").as[Option[String]]
    } yield AgentServiceCheck(
      checkID,
      name,
      args,
      dockerContainerID,
      shell,
      interval,
      timeout,
      ttl,
      http,
      header,
      method,
      body,
      tcp,
      status,
      notes,
      tlsSkipVerify,
      gRPC,
      gRPCUseTLS,
      aliasNode,
      aliasService,
      deregisterCriticalServiceAfter
    )
  }

  implicit val agentServiceChecksInfoDecoder: Decoder[AgentServiceChecksInfo] = new Decoder[AgentServiceChecksInfo] {
    override def apply(c: HCursor): Result[AgentServiceChecksInfo] = for {
      aggregatedStatus <- c.downField("AggregatedStatus").as[String]
      service <- c.downField("Service").as[AgentService]
      checks <- c.downField("Checks").as[List[HealthCheck]]
    } yield AgentServiceChecksInfo(aggregatedStatus, service, checks)
  }

  implicit val agentServiceConnectDecoder: Decoder[AgentServiceConnect] = new Decoder[AgentServiceConnect] {
    override def apply(c: HCursor): Result[AgentServiceConnect] = for {
      native <- c.downField("Native").as[Option[Boolean]]
      sedicarService <- c.downField("SedicarService").as[Option[AgentServiceRegistration]]
    } yield AgentServiceConnect(native, sedicarService)
  }

  implicit val agentServiceConnectEncoder: Encoder[AgentServiceConnect] = new Encoder[AgentServiceConnect] {
    override def apply(a: AgentServiceConnect): Json = Json.obj(
      ("Native", a.native.asJson),
      ("SedicarService", a.sedicarService.asJson)
    )
  }

  implicit val agentServiceConnectProxyConfigDecoder: Decoder[AgentServiceConnectProxyConfig] =
    new Decoder[AgentServiceConnectProxyConfig] {
      override def apply(c: HCursor): Result[AgentServiceConnectProxyConfig] = for {
        destinationServiceName <- c.downField("DestinationServiceName").as[Option[String]]
        destinationServiceID <- c.downField("DestinationServiceID").as[Option[String]]
        localServiceAddress <- c.downField("LocalServiceAddress").as[Option[String]]
        localServicePort <- c.downField("LocalServicePort").as[Option[Int]]
        config <- c.downField("Config").as[Option[Map[String, String]]]
        upstreams <- c.downField("Upstreams").as[Option[Upstream]]
      } yield AgentServiceConnectProxyConfig(
        destinationServiceName,
        destinationServiceID,
        localServiceAddress,
        localServicePort,
        config,
        upstreams
      )
    }

  implicit val agentServiceConnectProxyConfigEncoder: Encoder[AgentServiceConnectProxyConfig] =
    new Encoder[AgentServiceConnectProxyConfig] {
      override def apply(a: AgentServiceConnectProxyConfig): Json = Json.obj(
        ("DestinationServiceName", a.destinationServiceName.asJson),
        ("DestinationServiceID", a.destinationServiceID.asJson),
        ("LocalServiceAddress", a.localServiceAddress.asJson),
        ("LocalServicePort", a.localServicePort.asJson),
        ("Config", a.config.asJson),
        ("Upstreams", a.upstreams.asJson)
      )
    }

  implicit val agentServiceRegistrationDecoder: Decoder[AgentServiceRegistration] = new Decoder[AgentServiceRegistration] {
    override def apply(c: HCursor): Result[AgentServiceRegistration] = for {
      kind <- c.downField("Kind").as[Option[ServiceKind]]
      id <- c.downField("ID").as[Option[String]]
      name <- c.downField("Name").as[Option[String]]
      tags <- c.downField("Tags").as[Option[List[String]]]
      port <- c.downField("Port").as[Option[Int]]
      address <- c.downField("Address").as[Option[String]]
      taggedAddresses <- c.downField("TaggedAddresses").as[Option[Map[String, ServiceAddress]]]
      enableTagOverride <- c.downField("EnableTagOverride").as[Option[Boolean]]
      meta <- c.downField("Meta").as[Option[Map[String, String]]]
      weights <- c.downField("Weights").as[AgentWeights]
      check <- c.downField("Check").as[AgentServiceCheck]
      checks <- c.downField("Checks").as[List[AgentServiceCheck]]
      proxy <- c.downField("Proxy").as[Option[AgentServiceConnectProxyConfig]]
      connect <- c.downField("Connect").as[Option[AgentServiceConnect]]
      namespace <- c.downField("Namespace").as[Option[String]]
    } yield AgentServiceRegistration(
      kind,
      id,
      name,
      tags,
      port,
      address,
      taggedAddresses,
      enableTagOverride,
      meta,
      weights,
      check,
      checks,
      proxy,
      connect,
      namespace
    )
  }

  implicit val agentTokenDecoder: Decoder[AgentToken] = new Decoder[AgentToken] {
    override def apply(c: HCursor): Result[AgentToken] = for {
      token <- c.downField("Token").as[String]
    } yield AgentToken(token)
  }

  implicit val agentWeightsDecoder: Decoder[AgentWeights] = new Decoder[AgentWeights] {
    override def apply(c: HCursor): Result[AgentWeights] = for {
      passing <- c.downField("Passing").as[Int]
      warning <- c.downField("Warning").as[Int]
    } yield AgentWeights(passing, warning)
  }

  implicit val connectProxyConfigDecoder: Decoder[ConnectProxyConfig] = new Decoder[ConnectProxyConfig] {
    override def apply(c: HCursor): Result[ConnectProxyConfig] = for {
      proxyServiceId <- c.downField("ProxyServiceId").as[String]
      targetServiceId <- c.downField("TargetServiceId").as[String]
      targetServiceName <- c.downField("TargetServiceName").as[String]
      contentHash <- c.downField("ContentHash").as[String]
      config <- c.downField("Config").as[Map[String, String]]
      upstreams <- c.downField("Upstreams").as[List[Upstream]]
    } yield ConnectProxyConfig(
      proxyServiceId,
      targetServiceId,
      targetServiceName,
      contentHash,
      config,
      upstreams
    )
  }

  implicit val membersOptsDecoder: Decoder[MembersOpts] = new Decoder[MembersOpts] {
    override def apply(c: HCursor): Result[MembersOpts] = for {
      wan <- c.downField("WAN").as[Boolean]
      segment <- c.downField("Segment").as[String]
    } yield MembersOpts(wan, segment)
  }

  implicit val serviceRegisterOptsDecoder: Decoder[ServiceRegisterOpts] = new Decoder[ServiceRegisterOpts] {
    override def apply(c: HCursor): Result[ServiceRegisterOpts] = for {
      replaceExistingChecks <- c.downField("ReplaceExistingChecks").as[Boolean]
    } yield ServiceRegisterOpts(replaceExistingChecks)
  }

  implicit val upstreamDecoder: Decoder[Upstream] = new Decoder[Upstream] {
    override def apply(c: HCursor): Result[Upstream] = for {
      destinationType <- c.downField("DestinationType").as[Option[UpstreamDestType]]
      destinationNamespace <- c.downField("DestinationNamespace").as[Option[String]]
      destinationName <- c.downField("DestinationName").as[String]
      datacenter <- c.downField("Datacenter").as[Option[String]]
      localBindAddress <- c.downField("LocalBindAddress").as[Option[String]]
      localBindPort <- c.downField("LocalBindPort").as[Option[Int]]
      config <- c.downField("Config").as[Option[Map[String, String]]]
    } yield Upstream(
      destinationType,
      destinationNamespace,
      destinationName,
      datacenter,
      localBindAddress,
      localBindPort,
      config
    )
  }

  implicit val upstreamEncoder: Encoder[Upstream] = new Encoder[Upstream] {
    override def apply(a: Upstream): Json = Json.obj(
      ("DestinationType", a.destinationType.asJson),
      ("DestinationNamespace", a.destinationNamespace.asJson),
      ("DestinationName", a.destinationName.asJson),
      ("Datacenter", a.datacenter.asJson),
      ("LocalBindAddress", a.localBindAddress.asJson),
      ("LocalBindPort", a.localBindPort.asJson),
      ("Config", a.config.asJson)
    )
  }

  implicit val upstreamDestTypeDecoder: Decoder[UpstreamDestType] = new Decoder[UpstreamDestType] {
    override def apply(c: HCursor): Result[UpstreamDestType] = for {
      value <- c.as[String]
    } yield UpstreamDestType.withValue(value)
  }

  implicit val upstreamDestTypeEncoder: Encoder[UpstreamDestType] = new Encoder[UpstreamDestType] {
    override def apply(a: UpstreamDestType): Json = Json.fromString(a.value)
  }
}
