package consul4s

trait JsonEncoder {
  def catalogRegistrationToJson(): Unit

  def catalogDeregistrationToJson(): Unit

  def membersOptsToJson(): Unit
}
