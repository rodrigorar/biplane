package pt.rodrigorar.policies;

import pt.rodrigorar.caches.Entry;

public interface Policy<V> {
    boolean evaluate(Entry<V> entry);
}
