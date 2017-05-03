package ltp.service

import ltp.repository.SimpleLRUTimeEvictCache

/**
 * Created by phuonglam on 5/3/17.
 **/
trait FooService {
  def set(key: String, value: String)
  def get(key: String): String
}

class MapFooService extends FooService {

  protected var store = Map[String, String]()

  override def set(key: String, value: String): Unit = store = store + (key -> value)

  override def get(key: String): String = store(key)
}

case class CachedMapFooService() extends MapFooService with SimpleLRUTimeEvictCache[String, String] {
  override protected def loadData(k: String): String = store(k)

  override def get(key: String) = getCached(key)
}