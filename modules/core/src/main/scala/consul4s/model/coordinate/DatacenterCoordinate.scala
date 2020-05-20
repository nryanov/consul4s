package consul4s.model.coordinate

final case class DatacenterCoordinate(datacenter: String, areaId: String, coordinates: List[NodeCoordinate])
