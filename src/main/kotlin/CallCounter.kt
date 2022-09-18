import org.http4k.core.Filter
import java.util.concurrent.atomic.AtomicInteger

fun CallCounter(count: AtomicInteger) = Filter { next ->
    {
        count.getAndIncrement()
        next(it)
    }
}
