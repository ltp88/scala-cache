package ltp.repository

import ltp.service.CachedMapFooService
import org.junit.Test

/**
 * Created by phuonglam on 5/3/17.
 **/
class SimpleLRUTimeEvictCacheTest {

  @Test
  def testCache(): Unit = {
    val s = CachedMapFooService()
    s.set("1", "10")

    val value_1 = s.get("1")
    s.set("1", "40")
    val value_2 = s.get("1")

    assert(value_1 equals value_2)

    Thread.sleep(4000)

    val value_3 = s.get("1")

    assert(!(value_1 equals value_3))
  }
}
