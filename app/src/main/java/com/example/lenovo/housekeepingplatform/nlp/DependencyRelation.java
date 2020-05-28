package com.example.lenovo.housekeepingplatform.nlp;

/**
 * Created by lenovo on 2018/11/13.
 */

public enum DependencyRelation {

    S, // 核心(Main governor)
    SUBJ, // 主语(Subject)
    OBJ, // 宾语(Object)
    OBJ2, // 间接宾语(Indirect Object)
    SentObj, // 句子的宾语(Sentential object)
    ObjA, // 助动词(Auxiliary verb)
    COOR, // 并列标记(Coordinating adjunct)
    ATR, // 定语
    AVDA, // 状语(Adverbial)
    SUBOBJ, // 主体宾语(Subobject)
    VA, // 修饰动词的助动词(Verb adjunct)
    SOC, // 主语补语(Subject Complement)
    POBJ, // 介词宾语(Prepositional Object)
    TOP, // 主题(Topic)
    FC, // 方位结构补语(Postpositional Complement)
    COMP, // 补语(Complement)
    EPA, // 同位语(Epithet)
    DEC, // ‘的’的补充
    MA, // 数词结构(Numeral adjunct)
    DIC, // ‘地’的补充
    TA, // 时态附加语(Aspect adjunct)
    DFC, // ‘得’的补充，V(动词或充当谓语的形容词)+得+C(补语)
    ESA, // 句末附加语(Adjunct of sentence end)
    BaOBJ, // ‘把’的宾语。“把”字结构是“介词‘把’+名词、代词或词组”的语言结构。“把”字后边的名词、代词或词组叫作“把”字的后置成分。
    InA, // 插入语(Parenthesis)
    PLC, // 名词复数(Plural complement)
    CR, // 复句关系(Clause adjunct)
    OC, // 序数补足语(Ordinal complement)
    CsR, // 连接状语(Correlative adjunct)
    QC, // 数量Complement of classifier 如：三本、三岁、两个
    AuxR, // 助词附着关系(Particle adjunct)
    BeiS, // “被”字结构
    Punct, // 标点(Punctuation)
    Reason, //原因  如：怎么 不能
    Method, //方法
    Owner //领有者

}
