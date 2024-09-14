GRAPH_TEMPLATE = {
    'desc': {
        'slots': ['disease'],  # slot 插槽
        'question': '什么叫%disease%? / %disease%是一种什么病？',
        'cypher': "MATCH (n:Disease) WHERE n.name='%disease%' RETURN n.desc AS RES",
        'answer': '【%disease%】的定义：%RES%',
    },
    'cause': {
        'slots': ['disease'],
        'question': '%disease%一般是由什么引起的？/ 什么会导致%disease%？',
        'cypher': "MATCH (n:Disease) WHERE n.name='%disease%' RETURN n.cause AS RES",
        'answer': '【%disease%】的病因：%RES%',
    },
    'disease_symptom': {
        'slots': ['disease'],
        'question': '%disease%会有哪些症状？/ %disease%有哪些临床表现？',
        'cypher': "MATCH (n:Disease)-[:DISEASE_SYMPTOM]->(m) WHERE n.name='%disease%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(m.name) | s + '、' + x), 1) AS RES",
        'answer': '【%disease%】的症状：%RES%',
    },

    '''
    'disease_symptom' 'cypher' 解释：
    COLLECT(m.name): 这会收集与给定疾病相关的所有症状的名称（即 `m.name`），并将它们作为一个列表返回。  
    
    REDUCE(s = '', x IN ... | s + '、' + x): REDUCE 是一个聚合函数，它接受一个初始值（在这种情况下是一个空字符串 `''`）和一个表达式，
    然后遍历一个列表（在这种情况下是 `m.name` 的列表）。对于列表中的每个元素 `x`，它都会应用表达式 `s + '、' + x`，其中 `s` 是累积的结果，
    初始为 `''`。这样，它会将所有症状名称连接成一个由顿号分隔的字符串。  
    
    SUBSTRING(..., 1): 最后，SUBSTRING 函数用于删除结果字符串的开头顿号（如果有的话）。由于 REDUCE 会在每个症状名称前添加一个顿号，
    所以列表中的第一个症状名称前也会有一个不必要的顿号。SUBSTRING 函数通过从索引 1（即第二个字符）开始截取字符串来删除这个顿号。
    '''

    'symptom': {
        'slots': ['symptom'],
        'question': '%symptom%可能是得了什么病？',
        'cypher': "MATCH (n)-[:DISEASE_SYMPTOM]->(m:Symptom) WHERE m.name='%symptom%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(n.name) | s + '、' + x), 1) AS RES",
        'answer': '可能出现【%symptom%】症状的疾病：%RES%',
    },
    'cure_way': {
        'slots': ['disease'],
        'question': '%disease%吃什么药好得快？/ %disease%怎么治？',
        'cypher': '''
            MATCH (n:Disease)-[:DISEASE_CUREWAY]->(m1),
                (n:Disease)-[:DISEASE_DRUG]->(m2),
                (n:Disease)-[:DISEASE_DO_EAT]->(m3)
            WHERE n.name = '%disease%'
            WITH COLLECT(DISTINCT m1.name) AS m1Names, 
                COLLECT(DISTINCT m2.name) AS m2Names,
                COLLECT(DISTINCT m3.name) AS m3Names
            RETURN SUBSTRING(REDUCE(s = '', x IN m1Names | s + '、' + x), 1) AS RES1,
                SUBSTRING(REDUCE(s = '', x IN m2Names | s + '、' + x), 1) AS RES2,
                SUBSTRING(REDUCE(s = '', x IN m3Names | s + '、' + x), 1) AS RES3
            ''',
        'answer': '【%disease%】的治疗方法：%RES1%。\n可用药物：%RES2%。\n推荐食物：%RES3%',
    },
    'cure_department': {
        'slots': ['disease'],
        'question': '得了%disease%去医院挂什么科室的号？',
        'cypher': "MATCH (n:Disease)-[:DISEASE_DEPARTMENT]->(m) WHERE n.name='%disease%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(m.name) | s + '、' + x), 1) AS RES",
        'answer': '【%disease%】的就诊科室：%RES%',
    },
    'prevent': {
        'slots': ['disease'],
        'question': '%disease%要怎么预防？',
        'cypher': "MATCH (n:Disease) WHERE n.name='%disease%' RETURN n.prevent AS RES",
        'answer': '【%disease%】的预防方法：%RES%',
    },
    'not_eat': {
        'slots': ['disease'],
        'question': '%disease%换着有什么禁忌？/ %disease%不能吃什么？',
        'cypher': "MATCH (n:Disease)-[:DISEASE_NOT_EAT]->(m) WHERE n.name='%disease%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(m.name) | s + '、' + x), 1) AS RES",
        'answer': '【%disease%】的患者不能吃的食物：%RES%',
    },
    'check': {
        'slots': ['disease'],
        'question': '%disease%要做哪些检查？',
        'cypher': "MATCH (n:Disease)-[:DISEASE_CHECK]->(m) WHERE n.name='%disease%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(m.name) | s + '、' + x), 1) AS RES",
        'answer': '【%disease%】的检查项目：%RES%',
    },
    'cured_prob': {
        'slots': ['disease'],
        'question': '%disease%能治好吗？/ %disease%治好的几率有多大？',
        'cypher': "MATCH (n:Disease) WHERE n.name='%disease%' RETURN n.cured_prob AS RES",
        'answer': '【%disease%】的治愈率：%RES%',
    },
    'acompany': {
        'slots': ['disease'],
        'question': '%disease%的并发症有哪些？',
        'cypher': "MATCH (n:Disease)-[:DISEASE_ACOMPANY]->(m) WHERE n.name='%disease%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(m.name) | s + '、' + x), 1) AS RES",
        'answer': '【%disease%】的并发症：%RES%',
    },
    'indications': {
        'slots': ['drug'],
        'question': '%drug%能治那些病？',
        'cypher': "MATCH (n:Disease)-[:DISEASE_DRUG]->(m:Drug) WHERE m.name='%drug%' RETURN SUBSTRING(REDUCE(s = '', x IN COLLECT(n.name) | s + '、' + x), 1) AS RES",
        'answer': '【%drug%】能治疗的疾病有：%RES%',
    },
}
