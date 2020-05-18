package consul4s.model.query

import consul4s.model.health.ServiceEntry

final case class QueryResult(Service: String, Datacenter: String, DNS: DNS, Nodes: List[ServiceEntry])

/*
{
  "Service": "redis",
  "Nodes": [

    {
      "Node": {
        "ID": "40e4a748-2192-161a-0510-9bf59fe950b5",
        "Node": "foobar",
        "Address": "10.1.10.12",
        "Datacenter": "dc1",
        "TaggedAddresses": {
          "lan": "10.1.10.12",
          "wan": "10.1.10.12"
        },
        "NodeMeta": { "instance_type": "m3.large" }
      },

      "Service": {
        "ID": "redis",
        "Service": "redis",
        "Tags": null,
        "Meta": { "redis_version": "4.0" },
        "Port": 8000
      },


      "Checks": [
        {
          "Node": "foobar",
          "CheckID": "service:redis",
          "Name": "Service 'redis' check",
          "Status": "passing",
          "Notes": "",
          "Output": "",
          "ServiceID": "redis",
          "ServiceName": "redis"
        },
        {
          "Node": "foobar",
          "CheckID": "serfHealth",
          "Name": "Serf Health Status",
          "Status": "passing",
          "Notes": "",
          "Output": "",
          "ServiceID": "",
          "ServiceName": ""
        }
      ],


      "DNS": {
        "TTL": "10s"
      },
      "Datacenter": "dc3",
      "Failovers": 2
    }


  ]
}
 */
