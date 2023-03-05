package indiv.budin.rpc.irpc.balance.selectors;

import indiv.budin.rpc.irpc.common.utils.XXHash;
import indiv.budin.rpc.irpc.common.utils.hasher.FNV1a;
import indiv.budin.rpc.irpc.common.utils.hasher.Murmur3;
import indiv.budin.rpc.irpc.common.utils.hasher.XXHash64;
import indiv.budin.rpc.irpc.exception.HashException;
import org.apache.commons.codec.digest.XXHash32;

import javax.xml.bind.annotation.XmlType;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * @author
 * @date 2023/2/23 15 37
 * discription
 * references https://cloud.tencent.com/developer/article/1459776
 */
public class ConsistentHashSelector implements HashSelector<String,String> {
    private final Set<String> physicalNodes;
    private final static int VIRTUAL_COPIES = 128;
    private int virtual_copies;

    private final static String DEFAULT_HASH = "Murmur";
    private final TreeMap<Long, String> virtualNodes;

    private final int bit;

    private final String hashFunction;

    public static ConsistentHashSelector createDefault() {
        ConsistentHashSelector consistentHashSelector = create();
        consistentHashSelector.virtual_copies = VIRTUAL_COPIES;
        return consistentHashSelector;
    }

    public static ConsistentHashSelector create() {
        return new ConsistentHashSelector();
    }

    @Override
    public String select(String requestKey) {
        return getObjectNode(requestKey);
    }

    public ConsistentHashSelector() {
        this.bit = 64;
        hashFunction = DEFAULT_HASH;
        physicalNodes = new TreeSet<String>();
        virtualNodes = new TreeMap<>();
    }

    public ConsistentHashSelector(String hashFunc, int bit) {
        this.bit = bit;
        hashFunction = hashFunc;
        physicalNodes = new TreeSet<String>();
        virtualNodes = new TreeMap<>();
    }

    public ConsistentHashSelector(int bit) {
        this.bit = bit;
        hashFunction = DEFAULT_HASH;
        physicalNodes = new TreeSet<String>();
        virtualNodes = new TreeMap<>();
    }

    public ConsistentHashSelector(String hashFunc) {
        this.bit = 64;
        hashFunction = hashFunc;
        physicalNodes = new TreeSet<String>();
        virtualNodes = new TreeMap<>();
    }

    public ConsistentHashSelector injectNodes(List<String> nodes) {
        return injectNodes(nodes, VIRTUAL_COPIES);
    }

    public ConsistentHashSelector injectNodes(List<String> nodes, int virtual_copies) {
        this.virtual_copies = virtual_copies;
        for (String node : nodes) {
            addNode(node);
        }
        return this;
    }

    public void addNode(String node) {
        physicalNodes.add(node);
        for (int i = 0; i < virtual_copies; i++) {
            String key = nodeForm(node) + i;
            long hash = hash(key);
            virtualNodes.put(hash, node);
        }
    }

    @Override
    public void removeNode(String node) {
        if (!virtualNodes.containsKey(hash(nodeForm(node) + "0"))) return;
        physicalNodes.remove(node);
        for (int i = 0; i < virtual_copies; i++) {
            String key = nodeForm(node) + i;
            long hash = hash(key);
            virtualNodes.remove(hash);
        }
    }

    public boolean exist(String node){
        return physicalNodes.contains(node);
    }

    public String nodeForm(String node) {
        return node + "@";
    }


    private long hash(String key) {
        if (bit == 64) {
            return hash64(key);
        } else if (bit == 32) {
            return hash32(key);
        } else {
            throw new HashException(exceptionMessage());
        }
    }

    private String exceptionMessage() {
        return bit + " bit " + hashFunction + " hash function is unsupported";
    }

    private long hash64(String key) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        switch (hashFunction) {
            case "Murmur":
                return Murmur3.hash64(bytes);
            case "XXHash":
                return XXHash64.hash64(bytes);
            case "FNV":
                return FNV1a.hash64(bytes);
            default:
                throw new HashException(exceptionMessage());
        }
    }

    private int hash32(String key) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        switch (hashFunction) {
            case "Murmur":
                return Murmur3.hash32(bytes);
            case "FNV":
                return FNV1a.hash32(bytes);
            default:
                throw new HashException(exceptionMessage());
        }
    }


    // 查找对象映射的节点
    public String getObjectNode(String object) {
        long hash = hash(object);
        SortedMap<Long, String> tailMap = virtualNodes.tailMap(hash); // 所有大于 hash 的节点
        Long key = tailMap.isEmpty() ? virtualNodes.firstKey() : tailMap.firstKey();
        return virtualNodes.get(key);
    }

    // 统计对象与节点的映射关系
    public void dumpObjectNodeMap(String label, int objectMin, int objectMax) {
        // 统计
        Map<String, Integer> objectNodeMap = new TreeMap<>(); // IP => COUNT
        for (int object = objectMin; object <= objectMax; ++object) {
            String nodeIp = getObjectNode(Integer.toString(object));
            Integer count = objectNodeMap.get(nodeIp);
            objectNodeMap.put(nodeIp, (count == null ? 0 : count + 1));
        }

        // 打印
        double totalCount = objectMax - objectMin + 1;
        System.out.println("======== " + label + " ========");
        for (Map.Entry<String, Integer> entry : objectNodeMap.entrySet()) {
            long percent = (int) (100 * entry.getValue() / totalCount);
            System.out.println("IP=" + entry.getKey() + ": RATE=" + percent + "%");
        }
    }

    public static void main(String[] args) {
        List<String> nodes = new ArrayList<String>() {
            {
                add("192.168.1.101");
                add("192.168.1.102");
                add("192.168.1.103");
                add("192.168.1.104");
            }
        };
        ConsistentHashSelector consistentHashSelector = ConsistentHashSelector.createDefault().injectNodes(nodes);
        consistentHashSelector.dumpObjectNodeMap("初始情况", 0, 65536);

        // 删除物理节点
        consistentHashSelector.removeNode("192.168.1.103");
        consistentHashSelector.dumpObjectNodeMap("删除物理节点", 0, 65536);

        // 添加物理节点
        consistentHashSelector.addNode("192.168.1.108");
        consistentHashSelector.dumpObjectNodeMap("添加物理节点", 0, 65536);
    }
}
