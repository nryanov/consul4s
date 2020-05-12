package consul4s.model.deprecated

import consul4s.model.State

/*
Sample response:
    {
        "Node": "f2c0eb04d9df",
        "CheckID": "serfHealth",
        "Name": "Serf Health Status",
        "Status": "passing",
        "Notes": "",
        "Output": "Agent alive and reachable",
        "ServiceID": "",
        "ServiceName": "",
        "ServiceTags": [],
        // todo
        "Type": "",
        "Definition": {}
    }
 */
final case class NodeCheck(
  node: String,
  checkId: String,
  name: String,
  status: State,
  notes: String,
  output: String,
  serviceId: String,
  serviceName: String,
  serviceTags: List[String],
  namespace: Option[String]
)
