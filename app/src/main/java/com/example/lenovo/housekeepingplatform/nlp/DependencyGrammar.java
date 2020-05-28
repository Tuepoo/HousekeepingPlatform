package com.example.lenovo.housekeepingplatform.nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/11/13.
 */

public class DependencyGrammar {

    static final class POSSeq {
        public byte pos; // 本身的词性
        public int offset; // 支配词的偏移量
        public DependencyRelation relation; // 关系

        public POSSeq(byte p, int o, DependencyRelation r) {
            pos = p;
            offset = o;
            relation = r;
        }

        public String toString() {
            return "[ pos " + PartOfSpeech.getName(pos) + " offset " + offset
                    + "]";
        }
    }

    private static DependencyGrammar instance = new DependencyGrammar();

    public static DependencyGrammar getInstance() {
        return instance;
    }

    /**
     * An inner class of Ternary Search Trie that represents a node in the trie.
     */
    public final class TSTNode {
        /** The key to the node. */
        public ArrayList<POSSeq> data = null;

        /** The relative nodes. */
        protected TSTNode loKID;

        protected TSTNode eqKID;

        protected TSTNode hiKID;

        /** The char used in the split. */
        protected byte spliter;

        /**
         * Constructor method.
         *
         * @param seq
         *            The char used in the split.
         */
        protected TSTNode(byte seq) {
            this.spliter = seq;
        }

        public String toString() {
            return "splitchar:" + spliter;
        }
    }

    /** The base node in the trie. */
    public TSTNode root;

    /**
     * 依存规则
     */
    public DependencyGrammar() {
        ArrayList<POSSeq> seq = new ArrayList<POSSeq>();

        // 有条件吗
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.vn, 0, null));
        seq.add(new POSSeq(PartOfSpeech.n, -1, DependencyRelation.OBJ));
        seq.add(new POSSeq(PartOfSpeech.ry, -2, DependencyRelation.ESA));
        addRule(seq);

        // 用品交什么税
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.n, 1, DependencyRelation.Owner));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        seq.add(new POSSeq(PartOfSpeech.ry, 1, DependencyRelation.AVDA));
        seq.add(new POSSeq(PartOfSpeech.n, -2, DependencyRelation.OBJ));
        addRule(seq);

        // 不能退回，不能是依存词，退回是核心词。
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.ry, 2, DependencyRelation.Reason));
        seq.add(new POSSeq(PartOfSpeech.d, 1, DependencyRelation.AVDA));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        addRule(seq);

        // 不能退回，不能是依存词，退回是核心词。
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.d, 1, DependencyRelation.AVDA));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        addRule(seq);

        // 哪几个 海关，哪几个是依存词，海关是核心词。
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.ry, 1, DependencyRelation.ATR));
        seq.add(new POSSeq(PartOfSpeech.n, 0, null));
        addRule(seq);

        // 欧洲 那边，欧洲是依存词，那边是核心词。
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.ns, 1, DependencyRelation.ATR));
        seq.add(new POSSeq(PartOfSpeech.rz, 0, null));
        addRule(seq);

        // 是什么？是：动词充当谓语；什么：代词充当宾语
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.n, 1, DependencyRelation.SUBJ));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        seq.add(new POSSeq(PartOfSpeech.ry, -1, DependencyRelation.OBJ));
        addRule(seq);

        // 主要剧情
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.b, 1, DependencyRelation.ATR));
        seq.add(new POSSeq(PartOfSpeech.n, 0, null));
        addRule(seq);

        // 主谓宾
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.n, 1, DependencyRelation.SUBJ));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        seq.add(new POSSeq(PartOfSpeech.ry, -1, DependencyRelation.OBJ));
        addRule(seq);

        // 贵妃醉酒的
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.n, 1, DependencyRelation.ATR));
        seq.add(new POSSeq(PartOfSpeech.uj, 0, null));
        addRule(seq);

        // 主谓
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.n, 1, DependencyRelation.SUBJ));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        addRule(seq);

        //新港 这边  清关
        seq = new ArrayList<POSSeq>();
        seq.add(new POSSeq(PartOfSpeech.ns, 1, DependencyRelation.ATR));
        seq.add(new POSSeq(PartOfSpeech.rz, 1, DependencyRelation.SUBJ));
        seq.add(new POSSeq(PartOfSpeech.v, 0, null));
        addRule(seq);
    }

    public void addRule(ArrayList<POSSeq> key) {
        if (root == null) {
            root = new TSTNode(key.get(0).pos);
        }
        TSTNode node = null;
        if (key.size() > 0 && root != null) {
            TSTNode currentNode = root;
            int charIndex = 0;
            while (true) {
                if (currentNode == null)
                    break;
                int charComp = key.get(charIndex).pos - currentNode.spliter;
                if (charComp == 0) {
                    charIndex++;
                    if (charIndex == key.size()) {
                        node = currentNode;
                        break;
                    }
                    currentNode = currentNode.eqKID;
                } else if (charComp < 0) {
                    currentNode = currentNode.loKID;
                } else {
                    currentNode = currentNode.hiKID;
                }
            }
            ArrayList<POSSeq> occur2 = null;
            if (node != null) {
                occur2 = node.data;
            }
            if (occur2 != null) {
                // occur2.insert(pi);
                return;
            }
            currentNode = getOrCreateNode(key);
            currentNode.data = key;
        }
    }

    public ArrayList<POSSeq> matchLong(List<TreeNode> key, int offset) {
        if (key == null || root == null || "".equals(key) || offset >= key.size()) {
            return null;
        }

        // logger.debug("enter matchLong");
        int ret = offset;
        ArrayList<POSSeq> retPOS = null;

        TSTNode currentNode = root;
        int charIndex = offset;
        while (true) {
            if (currentNode == null) {
                // logger.debug("ret "+ret);
                // matchRet.end = ret;
                // matchRet.lhs = retPOS;
                return retPOS;
            }
            int charComp = key.get(charIndex).pos - currentNode.spliter;

            // logger.debug("key: "+key.get(charIndex).root.pos+" tree: "+
            // currentNode.splitchar.pos +" match charComp "+charComp);
            if (charComp == 0) {
                charIndex++;

                if (currentNode.data != null && charIndex > ret) {
                    ret = charIndex;
                    retPOS = currentNode.data;
                    // logger.debug("ret pos:"+retPOS);
                }
                if (charIndex == key.size()) {
                    return retPOS;
                }
                currentNode = currentNode.eqKID;
            } else if (charComp < 0) {
                currentNode = currentNode.loKID;
            } else {
                currentNode = currentNode.hiKID;
            }
        }
    }

    public static void merge(List<TreeNode> key, int offset,
                             ArrayList<POSSeq> seq) {
        // 首先建立起层次关系和支配连接，然后删除顶层中多余的子树引用
        for (int i = 0; i < seq.size(); ++i) {
            TreeNode currentTree = key.get(offset + i);
            POSSeq p = seq.get(i);
            if (p.offset != 0) {
                TreeNode parentTree = key.get(offset + i + p.offset);

                // 建立支配连接
                currentTree.governor = parentTree;
                currentTree.relation = p.relation;

                // 建立起层次
                if (parentTree.order == null) {
                    parentTree.order = new ArrayList<TreeNode>();
                }

                // 如果父节点缺位，先把它自己加进去
                if (p.offset < 0 && parentTree.order.size() == 0) {
                    parentTree.order.add(parentTree);
                    //logger.debug("add parent " + parentTree);
                }

                //if(p.offset<0)
                //	parentTree.order.add(currentTree);
                //else
                //	parentTree.order.add(0,currentTree);
                //logger.debug("add child " + currentTree);
                currentTree.check();  //检查完整性
                parentTree.addChild(currentTree);
                parentTree.maxId = Math.max(parentTree.id, currentTree.id);
            } else {
                if (currentTree.order == null) {
                    currentTree.order = new ArrayList<TreeNode>();
                }

                //子层没有再加本词
                if(currentTree.order.indexOf(currentTree)<0)
                    currentTree.order.add(currentTree);
            }
        }

        // 删除顶层中多余的子树引用
        int i = 0;
        for (POSSeq p : seq) {
            if (p.offset != 0) {
                key.remove(offset + i);
                offset--;
            }
            ++i;
        }

    }

    /**
     * Returns the node indexed by key, creating that node if it doesn't exist,
     * and creating any required intermediate nodes if they don't exist.
     *
     * @param key
     *            A <code>String</code> that indexes the node that is returned.
     * @return The node object indexed by key. This object is an instance of an
     *         inner class named <code>TernarySearchTrie.TSTNode</code>.
     * @exception NullPointerException
     *                If the key is <code>null</code>.
     * @exception IllegalArgumentException
     *                If the key is an empty <code>String</code>.
     */
    protected TSTNode getOrCreateNode(ArrayList<POSSeq> key)
            throws NullPointerException, IllegalArgumentException {
        if (key == null) {
            throw new NullPointerException(
                    "attempt to get or create node with null key");
        }
        if ("".equals(key)) {
            throw new IllegalArgumentException(
                    "attempt to get or create node with key of zero length");
        }
        if (root == null) {
            root = new TSTNode(key.get(0).pos);
        }
        TSTNode currentNode = root;
        int charIndex = 0;
        while (true) {
            int charComp = key.get(charIndex).pos - currentNode.spliter;
            if (charComp == 0) {
                charIndex++;
                if (charIndex == key.size()) {
                    return currentNode;
                }
                if (currentNode.eqKID == null) {
                    currentNode.eqKID = new TSTNode(key.get(charIndex).pos);
                }
                currentNode = currentNode.eqKID;
            } else if (charComp < 0) {
                if (currentNode.loKID == null) {
                    currentNode.loKID = new TSTNode(key.get(charIndex).pos);
                }
                currentNode = currentNode.loKID;
            } else {
                if (currentNode.hiKID == null) {
                    currentNode.hiKID = new TSTNode(key.get(charIndex).pos);
                }
                currentNode = currentNode.hiKID;
            }
        }
    }

}
