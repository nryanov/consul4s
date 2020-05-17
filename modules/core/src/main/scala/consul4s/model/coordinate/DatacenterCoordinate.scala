package consul4s.model.coordinate

final case class DatacenterCoordinate(Datacenter: String, AreaID: String, Coordinates: List[NodeCoordinate])
