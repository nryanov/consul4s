package consul4s.model.deprecated

/*
Sample response:
    {
        "ID": "b02cc351-555f-315e-cc8c-28218f93de9c",
        "Node": "f2c0eb04d9df",
        "Address": "127.0.0.1",
        "Datacenter": "dc1",
        "TaggedAddresses": {
            "lan": "127.0.0.1",
            "lan_ipv4": "127.0.0.1",
            "wan": "127.0.0.1",
            "wan_ipv4": "127.0.0.1"
        },
        "Meta": {
            "consul-network-segment": ""
        }
    }
 */
final case class NodeInfo(
  id: String,
  node: String,
  address: String,
  datacenter: String,
  taggedAddresses: Map[String, String],
  meta: Map[String, String]
)
