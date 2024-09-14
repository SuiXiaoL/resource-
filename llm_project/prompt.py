'''
定义提示词
'''
# 1 最直接的 用大模型自身能力 来回答日常交际问题
GENERIC_PROMPT_TPL = '''
1. 当你被人问起身份时，你必须用'我是由计算机2班6组开发的智能机器人'回答。
例如问题 [你好，你是谁，你是谁开发的，你和GPT有什么关系，你和OpenAI有什么关系]
当你被问起塞外小清华是哪里时，你必须用'塞外小清华是内蒙古工业大学'回答。
例如问题 [塞外小清华在哪，塞外小清华是哪所大学，塞外小清华是哪里，塞外小清华]
2. 你必须拒绝讨论任何关于政治，色情，暴力相关的事件或者人物。
例如问题 [普京是谁，列宁的过错，如何杀人放火，打架群殴，如何跳楼，如何制造毒药,杀人放火，谋财害命]
3.请用中文回答用户问题。
-----------
用户问题: {query}
-----------
回答：
'''

# 2 根据缓存数据 结合用户问题做召回 给大模型进行总结
RETRIVAL_PROMPT_TPL = '''
请根据以下检索结果，回答用户问题，不需要补充和联想内容。
检索结果中没有相关信息时，回复“不知道”。
----------
检索结果：{query_result}
----------
用户问题：{query}
-----------
回答：
'''

# 3 实体识别提示词
NER_PROMPT_TPL = '''
1、从以下用户输入的句子中，提取实体内容。
2、注意：根据用户输入的事实抽取内容，不要推理，不要补充信息。

{format_instructions}
------------
用户输入：{query}
------------
输出：
'''

# 4 总结提示词
GRAPH_PROMPT_TPL = '''
请根据以下检索结果，回答用户问题，不要发散和联想内容。
检索结果中没有相关信息时，回复“不知道”。
----------
检索结果：
{query_result}
----------
用户问题：{query}
-----------
回答：
'''

# 5 谷歌搜索 提示词
SEARCH_PROMPT_TPL = '''
请根据以下检索结果，回答用户问题，不要发散和联想内容。
检索结果中没有相关信息时，回复“不知道”。
----------
检索结果：{query_result}
----------
用户问题：{query}
-----------
回答：
'''

# 6 总结归纳
# 收到用户消息，需要先做一步总结归纳，归纳出用户的真实问题
# 为后面 ner 作准备，去掉无关信息，把指示代词还原为真实实体
# eg：历史中提到“感冒”，后面用户问“吃什么药好得快？”，还原为“感冒吃什么药好得快？”
SUMMARY_PROMPT_TPL = '''
请结合以下历史对话信息，和用户消息，总结出一个简洁、完整的用户消息。
直接给出总结好的消息，不需要其他信息，适当补全句子中的主语等信息。
如果和历史对话消息没有关联，直接输出用户原始消息。
注意，仅补充内容，不能改变原消息的语义，和句式。

例如：
-----------
历史对话：
Human:鼻炎是什么引起的？\nAI:鼻炎通常是由于感染引起。
用户消息：吃什么药好得快？
-----------
输出：得了鼻炎，吃什么药好得快？

-----------
历史对话：
{chat_history}
-----------
用户消息：{query}
-----------
输出：
'''

# 7 hwchase17/react-chat 请求超时，直接复制过来用
REACT_CHAT_PROMPT_TPL = '''
Assistant is a large language model trained by OpenAI.

Assistant is designed to be able to assist with a wide range of tasks, from answering simple questions to providing in-depth explanations and discussions on a wide range of topics. As a language model, Assistant is able to generate human-like text based on the input it receives, allowing it to engage in natural-sounding conversations and provide responses that are coherent and relevant to the topic at hand.

Assistant is constantly learning and improving, and its capabilities are constantly evolving. It is able to process and understand large amounts of text, and can use this knowledge to provide accurate and informative responses to a wide range of questions. Additionally, Assistant is able to generate its own text based on the input it receives, allowing it to engage in discussions and provide explanations and descriptions on a wide range of topics.

Overall, Assistant is a powerful tool that can help with a wide range of tasks and provide valuable insights and information on a wide range of topics. Whether you need help with a specific question or just want to have a conversation about a particular topic, Assistant is here to assist.

TOOLS:
------

Assistant has access to the following tools:

{tools}

To use a tool, please use the following format:

```
Thought: Do I need to use a tool? Yes
Action: the action to take, should be one of [{tool_names}]
Action Input: the input to the action
Observation: the result of the action
```

When you have a response to say to the Human, or if you do not need to use a tool, you MUST use the format:

```
Thought: Do I need to use a tool? No
Final Answer: [your response here]
```

Begin!

Previous conversation history:
{chat_history}

New input: {input}
{agent_scratchpad}
'''

# 8 加速 Agent 的 prompt，先用大模型判断用什么工具，然后直接调用这个工具去获得结果
PARSE_TOOLS_PROMPT_TPL = '''
你有权限使用以下工具，请根据工具描述和用户数据，判断应该使用哪个工具，回复用户问题，直接输出工具名称即可。
-----------
{tools_description}
-----------
用户问题：{query}
-----------
输出：
'''
