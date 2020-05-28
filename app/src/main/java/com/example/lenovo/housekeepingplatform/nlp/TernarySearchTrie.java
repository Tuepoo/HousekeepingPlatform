package com.example.lenovo.housekeepingplatform.nlp;

import com.example.lenovo.housekeepingplatform.application.housekeepingApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by lenovo on 2018/11/10.
 */

public class TernarySearchTrie {

    public static final class TSTNode {
        protected TSTNode left;
        protected TSTNode mid;
        protected TSTNode right;

        public char splitChar;
        protected String pos;//存储词性
        protected String nodeValue; // 是否可以结束

        public TSTNode(char key) {
            // System.out.println("create node:"+key);
            this.splitChar = key;
        }

        public String toString() {
            return "Node spliter:" + splitChar+" 可以结束?"+(nodeValue!=null);
        }
    }

    private static TSTNode root;

    static {//加载词典
        String fileName = "baseWords.txt";//"WordList.txt";
        try {
            //FileReader filereadnew = new FileReader(fileName);
            //BufferedReader read = new BufferedReader(filereadnew);
//            FileInputStream file = new FileInputStream(fileName);

//            BufferedReader in = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            InputStream file = housekeepingApplication.getContext().getResources().getAssets().open(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    if("".equals(line))
                        continue;
                    StringTokenizer st = new StringTokenizer(line, ":");
                    String key = st.nextToken();
                    String pos = st.nextToken();
                    TSTNode currentNode = createNode(key);
                    currentNode.nodeValue = key;
                    currentNode.pos = pos;
                    // System.out.println(currentNode);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建给定词相关的节点并返回对应的叶结点
    public static TSTNode createNode(String key) {
        int charIndex = 0; // 当前要比较的字符在查询词中的位置
        char currentChar = key.charAt(charIndex); // 当前要比较的字符
        if (root == null) {
            root = new TSTNode(currentChar);
        }
        TSTNode currentNode = root;
        while (true) {
            // 比较词的当前字符与节点的当前字符
            int compa = currentChar - currentNode.splitChar;
            if (compa == 0) { // 词中的字符与节点中的字符相等
                charIndex++;
                if (charIndex == key.length()) {
                    return currentNode;
                }
                currentChar = key.charAt(charIndex);
                if (currentNode.mid == null) {
                    currentNode.mid = new TSTNode(currentChar);
                }
                currentNode = currentNode.mid; // 向下找
            } else if (compa < 0) { // 词中的字符小于节点中的字符
                if (currentNode.left == null) {
                    currentNode.left = new TSTNode(currentChar);
                }
                currentNode = currentNode.left; // 向左找
            } else { // 词中的字符大于节点中的字符
                if (currentNode.right == null) {
                    currentNode.right = new TSTNode(currentChar);
                }
                currentNode = currentNode.right; // 向右找
            }
        }
    }

    String text = null; //切分文本
    int offset; //已经处理到的位置

    public TernarySearchTrie(String text)  {
        this.text = text;
        offset = 0;
    }

    public boolean hasWord(){
        if (offset >= text.length()) //已经处理完毕-+
            return false;
        return true;
    }

    public WordToken nextWord() { //得到下一个词
        //System.out.println("offset "+offset);
        WordToken word = null; //候选词
        if (text == null || root == null) {
            return word;
        }

        if (offset >= text.length()) //已经处理完毕-+
            return null;
        TSTNode currentNode = root; //当前节点
        int charIndex = offset; //当前位置
        while (true) {
            //System.out.println("当前字符 "+charIndex+" :"+text.charAt(charIndex)+" 当前节点:"+currentNode);

            if (currentNode == null) {//已经匹配完毕
                if(word==null){ //没有匹配上词典上的任何词，则按单字切分
//                    word = new WordToken(text.substring(offset,offset+1),offset,charIndex-1,PartOfSpeech.values.get(currentNode.pos));
                    word = new WordToken(text.substring(offset,offset+1),PartOfSpeech.values.get(currentNode.pos));
//                    word.termText = text.substring(offset,offset+1);
//                    word.start = offset;
//                    word.end = charIndex-1;
//                    word.type = PartOfSpeech.values.get(currentNode.pos);
                    offset++;
                }
                return word;
            }
            //待切分文本中的当前字符和当前节点中的字符比较
            int charComp = text.charAt(charIndex) - currentNode.splitChar;

            if (charComp == 0) {
                charIndex++;

                if (currentNode.nodeValue != null) { //可以结束节点
//                    word = new WordToken(currentNode.nodeValue,offset,charIndex-1,PartOfSpeech.values.get(currentNode.pos));
                    word = new WordToken(currentNode.nodeValue,PartOfSpeech.values.get(currentNode.pos));
//                    word.termText = currentNode.nodeValue; // 候选最长匹配词
//                    word.start = offset;
//                    word.end = charIndex-1;
//                    word.type = PartOfSpeech.values.get(currentNode.pos);
                    offset = charIndex;
                }
                if (charIndex == text.length()) {
                    return word; // 已经匹配完
                }
                currentNode = currentNode.mid;
            } else if (charComp < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
    }

}
