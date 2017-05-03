package ltp.repository

import java.util.concurrent.TimeUnit

import com.google.common.cache._

import scala.collection.JavaConverters._

/**
 * @author phuonglt
 */
trait SimpleLRUTimeEvictCache[K <: Object, V <: Object] {

  protected val cache = {
    val builder = CacheBuilder.newBuilder()
      .maximumSize(maxSize)

    expiredAfterWrite match {
      case Some(s) => builder.expireAfterWrite(s._1, s._2)
      case _ => ;
    }

    expiredAfterAccess match {
      case Some(s) => builder.expireAfterAccess(s._1, s._2)
      case _ => ;
    }

    refreshAfterWrite match {
      case Some(s) => builder.refreshAfterWrite(s._1, s._2)
    }

    builder.removalListener(new RemovalListener[K, V] {
      override def onRemoval(notification: RemovalNotification[K, V]): Unit = removal(notification)
    })

    builder.build[K, V](new CacheLoader[K, V] {
      override def load(key: K): V = loadData(key)
    })
  }

  /** Eviction */
  protected def maxSize: Long = 1000L

  protected def expiredAfterWrite: Option[(Long, TimeUnit)] = Some(5L, TimeUnit.MINUTES) // 5 mins

  protected def expiredAfterAccess: Option[(Long, TimeUnit)] = None

  /** Refresh */
  protected def refreshAfterWrite: Option[(Long, TimeUnit)] = None

  /** Removal notification */
  protected def removal(notification: RemovalNotification[K, V]) = {}

  /** CRUD data */
  protected def loadData(k: K): V

  protected def getData(k: K): V = cache.getIfPresent(k) match {
    case null =>
      val value = loadData(k)
      cache.put(k, value)
      value
    case x => x
  }

  protected def remove(k: K) = cache.invalidate(k)

  protected def remove(k: Iterable[K]) = cache.invalidateAll(k.asJava)

  protected def removeAll() = cache.invalidateAll()

  /** Public function */
  def getCached(k: K) = getData(k)

  /** TimeUnit */
  protected def NANOSECONDS = TimeUnit.NANOSECONDS
  protected def MICROSECONDS = TimeUnit.MICROSECONDS
  protected def MILLISECONDS = TimeUnit.MILLISECONDS
  protected def SECONDS = TimeUnit.SECONDS
  protected def MINUTES = TimeUnit.MINUTES
  protected def HOURS = TimeUnit.HOURS
  protected def DAYS = TimeUnit.DAYS
}