package com.example.lenovo.housekeepingplatform.nlp;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by lenovo on 2018/11/13.
 */

public class TreeNode {

//    private static Logger logger = Logger.getLogger(TreeNode.class);


    public int id; //在句子中唯一的编号
    public String term; //词本身
    public byte pos;  //词性

    public TreeNode governor; //支配词
    public DependencyRelation relation; //依存关系
    public ArrayList<TreeNode> order; //树结构
    public int maxId;
    public WordToken token;

    public TreeNode(WordToken t) {
        term = t.termText;
//        id = t.start;
        pos = t.type;
        token = t;
    }

    public TreeNode(String t,int i) {
        term = t;
        id = i;
    }

    public void addChild(TreeNode n){
        //logger.debug(" add child "+n);
        int pos =0;
        for(int i=0;i<order.size();++i){
            TreeNode c= order.get(i);

            //logger.debug("c.id "+c.id + " n.id "+n.id);
            if(c.id>n.id){
                //logger.debug("break c.id "+c.id + " n.id "+n.id );
                break;
            }
            else{
                pos = i+1;
            }

        }
        //logger.debug(" pos "+pos);
        order.add(pos,n);
        //logger.debug(" order add child "+order);
    }

    public String getFirstChild(){
        for(TreeNode n:order){
            if(!n.equals(this)){
                return n.term;
            }
        }
        return null;
    }

    public String getChild(DependencyRelation r){
        if(order==null)
            return null;
        for(TreeNode n:order){
            if(!n.equals(this) && n.relation == r){
                return n.term;
            }
        }
        return null;
    }

    public void check(){
        if(order!=null){
            if(order.indexOf(this)<0) //不完整
                order.add(this);  //节点自己加进去
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("编号:"+id+" 词:"+term);
        if(governor!=null)
            sb.append(" 支配词:"+governor.term);
        sb.append(" 依存关系:"+relation);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof TreeNode)) //判断传入对象的类型
            return false;
        TreeNode that = (TreeNode)o;
        //logger.debug("root id "+this.root.id + " that.root.id "+that.root.id);
        return (this.id == that.id);
    }

    public TreeNode findNode(byte p){
        if(this.pos==p)
            return this;
        if(order == null)
            return null;

        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(this);

        for (TreeNode headNode = queue.poll();headNode!=null;headNode = queue.poll()) {
            int head = headNode.order.indexOf(headNode);




            for (int k = 0; k <head; ++k) {
                TreeNode currentNode = headNode.order.get(k);

                if(currentNode.pos==p){
                    return currentNode;
                }
                if(currentNode.order!=null){
                    queue.add(currentNode);
                }
            }

            for (int k = (head + 1); k < headNode.order.size(); ++k) {

                TreeNode currentNode = headNode.order.get(k);

                if(currentNode.pos==p){
                    return currentNode;
                }

                if(currentNode.order!=null){
                    queue.add(currentNode);
                }
            }

        }

        return null;
    }

    //输出结构
    public void getStructure(){
        if(order == null)
            return;

        ArrayList<TermNode> result = new ArrayList<TermNode>();
        result.add(new TermNode(this));

        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(this);

        for (TreeNode headNode = queue.poll();headNode!=null;headNode = queue.poll()) {
            int head = headNode.order.indexOf(headNode);




            for (int k = 0; k <head; ++k) {

                TreeNode currentNode = headNode.order.get(k);

                if(currentNode.order!=null){
                    queue.add(currentNode);
                }
            }

            for (int k = (head + 1); k < headNode.order.size(); ++k) {

                TreeNode currentNode = headNode.order.get(k);

                if(currentNode.order!=null){
                    queue.add(currentNode);
                }
            }
        }
    }

    // 从依存文法树的根节点生成句子
    public String toSentence(){
        if(order == null)
            return term;

        ArrayList<TermNode> result = new ArrayList<TermNode>();
        result.add(new TermNode(this));

        //ArrayList<TermNode> leftBorder = new ArrayList<TermNode>();

        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(this);

        for (TreeNode headNode = queue.poll();headNode!=null;headNode = queue.poll()) {
            int head = headNode.order.indexOf(headNode);



            int resultPos = result.indexOf(new TermNode(headNode));

            for (int k = 0; k <head; ++k) {
                TreeNode currentNode = headNode.order.get(k);
                result.add(resultPos, new TermNode(currentNode));
                if(currentNode.order!=null){
                    queue.add(currentNode);
                }
                ++resultPos;
            }

            ++resultPos;
            for (int k = (head + 1); k < headNode.order.size(); ++k) {
                TreeNode currentNode = headNode.order.get(k);
                result.add(resultPos, new TermNode(currentNode));
                if(currentNode.order!=null){
                    queue.add(currentNode);
                }
                ++resultPos;
            }

            StringBuilder sb = new StringBuilder();
            for(TermNode n:result){
                sb.append(n.term +" ");
            }

        }

        StringBuilder sb = new StringBuilder();
        for(TermNode n:result){
            sb.append(n.term +" ");
        }
        return sb.toString();
    }

}
