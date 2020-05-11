package consul4s.model

/*
Sample response:
    {
        "Name": "f2c0eb04d9df",
        "Addr": "127.0.0.1",
        "Port": 8301,
        "Tags": {
            "acls": "0",
            "build": "1.7.3:8b4a3d95",
            "dc": "dc1",
            "id": "b02cc351-555f-315e-cc8c-28218f93de9c",
            "port": "8300",
            "raft_vsn": "3",
            "role": "consul",
            "segment": "",
            "vsn": "2",
            "vsn_max": "3",
            "vsn_min": "2",
            "wan_join_port": "8302"
        },
        "Status": 1,
        "ProtocolMin": 1,
        "ProtocolMax": 5,
        "ProtocolCur": 2,
        "DelegateMin": 2,
        "DelegateMax": 5,
        "DelegateCur": 4
    }
 */
final case class MemberInfo(
  name: String,
  addr: String,
  port: Int,
  tags: Map[String, String],
  status: Int,
  protocolMin: Int,
  protocolMax: Int,
  protocolCur: Int,
  delegateMin: Int,
  delegateMax: Int,
  delegateCur: Int
)
