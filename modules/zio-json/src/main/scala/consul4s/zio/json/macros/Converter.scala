package consul4s.zio.json.macros

/**
 * @tparam A - class representation with consul specific field names
 * @tparam B - class that will be used as a return type from api calls
 *
 * The order of fields should be the same (no checks additional checks for this now)
 */
trait Converter[A, B] {
  def from(a: A): B

  def to(b: B): A
}
