package consul4s.model.deprecated

/*
Sample response:
{
            "ID": "consul",
            "Service": "consul",
            "Tags": [],
            "Address": "",
            "Meta": {
                "raft_version": "3",
                "serf_protocol_current": "2",
                "serf_protocol_max": "5",
                "serf_protocol_min": "1",
                "version": "1.7.3"
            },
            "Port": 8300,
            "Weights": {
                "Passing": 1,
                "Warning": 1
            },
            "EnableTagOverride": false,
            "Proxy": {
                "MeshGateway": {},
                "Expose": {}
            },
            "Connect": {}
        }
 */
final case class ServiceInfo(
  id: String,
  service: String,
  tags: List[String],
  address: String,
  meta: Map[String, String],
  port: Int,
  weights: Map[String, Int],
  enableTagOverride: Boolean,
  proxy: Map[String, Map[String, String]],
  connect: Map[String, String]
)
