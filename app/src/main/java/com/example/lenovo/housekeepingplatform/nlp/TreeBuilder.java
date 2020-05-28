package com.example.lenovo.housekeepingplatform.nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2018/11/17.
 */

public class TreeBuilder {

    static DependencyGrammar grammar = DependencyGrammar.getInstance();

    // 输入：分词得到的词序列
    public static ArrayList<TreeNode> getDepTree(WordToken[] tokens) {
        // 匹配词性，得到依存树的结构
        ArrayList<TreeNode> depTrees = new ArrayList<TreeNode>();
        for (WordToken t : tokens) {
            TreeNode node = new TreeNode(t);

            TreeNode d = node;

            depTrees.add(d);
        }

        //TODO:死循环检测
        boolean findNew = true;
        while (findNew) {
            findNew = false;
            for (int offset = 0; offset < depTrees.size(); ++offset) {
                ArrayList<DependencyGrammar.POSSeq> rule = grammar.matchLong(
                        depTrees, offset);
                if (rule != null) {
                    findNew = true;
                    //logger.debug("rule:"+rule);
                    DependencyGrammar.merge(depTrees, offset, rule);
                }
            }
        }
        return depTrees;
    }

    // 输入：分词得到的词序列
    public static ArrayList<TreeNode> getDepTree(ArrayList<WordToken> tokens) {
        // 匹配词性，得到依存树的结构
        ArrayList<TreeNode> depTrees = new ArrayList<TreeNode>();
        for (WordToken t : tokens) {
            if(t != null) {
                TreeNode node = new TreeNode(t);

                TreeNode d = node;//new DepTree(node);

                depTrees.add(d);
            }
        }

        //TODO:死循环检测
        boolean findNew = true;
        while (findNew) {
            findNew = false;
            for (int offset = 0; offset < depTrees.size(); ++offset) {
                ArrayList<DependencyGrammar.POSSeq> rule = grammar.matchLong(
                        depTrees, offset);
                if (rule != null) {
                    findNew = true;

                    DependencyGrammar.merge(depTrees, offset, rule);
                }
            }
        }
        return depTrees;
    }

    public static List<TreeNode> getDepTree(TreeNode[] trees) {
        ArrayList<TreeNode> depTrees = new ArrayList<TreeNode>();
        depTrees.addAll(Arrays.asList(trees));
        boolean findNew = true;
        while (findNew) {
            findNew = false;
            for (int offset = 0; offset < depTrees.size(); ++offset) {
                ArrayList<DependencyGrammar.POSSeq> rule = grammar.matchLong(
                        depTrees, offset);
                if (rule != null) {
                    findNew = true;
                    //logger.debug("rule:"+rule);
                    DependencyGrammar.merge(depTrees, offset, rule);
                }
            }
        }
        return depTrees;
    }

}
