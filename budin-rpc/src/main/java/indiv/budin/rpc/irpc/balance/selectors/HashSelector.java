package indiv.budin.rpc.irpc.balance.selectors;

public interface HashSelector<K,V> {
    public V select(K key);

    public void removeNode(K key);

}
