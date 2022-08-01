import threading
from wrapt import decorator


@decorator
def synchronized(wrapped, instance, args, kwargs):
    if instance is None:
        owner = wrapped
    else:
        owner = instance

    lock = vars(owner).get('_synchronized_lock', None)

    if lock is None:
        meta_lock = vars(synchronized).setdefault(
            '_synchronized_meta_lock', threading.Lock())

        with meta_lock:
            lock = vars(owner).get('_synchronized_lock', None)
            if lock is None:
                # lock = threading.RLock()
                lock = threading.Lock()
                setattr(owner, '_synchronized_lock', lock)

    with lock:
        return wrapped(*args, **kwargs)
