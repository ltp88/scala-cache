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
    s.set("2", "20")
    s.set("3", "30")

    val value1 = s.get("1")
    s.set("1", "40")
    val value2 = s.get("1")

    assert(value1 equals value2)
  }
}
