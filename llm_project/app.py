import shutup
shutup.please()

import gradio as gr
from service import Service

def doctor_bot(message, history):
    service = Service()
    return service.answer(message, history)

# 自定义 CSS 样式
css = '''
.gradio-container {
    max-width: 1000px !important;
    margin: 20px auto !important;
    background-color: #00e1ff !important; /* 背景颜色 */
    border-radius: 10px !important; /* 圆角 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1) !important; /* 阴影 */
    padding: 20px !important;
}

.message {
    padding: 10px !important;
    font-size: 14px !important;
    color: #333333 !important; /* 文本颜色 */
}

/* 自定义按钮样式 */
button.gradio-button {
    background-color: #422222 !important; /* 按钮背景颜色 */
    color: white !important; /* 按钮文字颜色 */
    border: none !important;
    border-radius: 5px !important;
    padding: 10px 20px !important;
    font-size: 16px !important;
    cursor: pointer !important;
}

button.gradio-button:hover {
    background-color: #000000 !important; /* 按钮悬停效果 */
}

/* 自定义输入框样式 */
.textbox input {
    border: 1px solid #cccccc !important;
    border-radius: 5px !important;
    padding: 10px !important;
    width: calc(100% - 22px) !important; /* 调整宽度以考虑内边距和边框 */
}

.textbox input:focus {
    border-color: #000000 !important; /* 输入框聚焦时的边框颜色 */
    outline: none !important;
}
'''

demo = gr.ChatInterface(
    css=css,
    fn=doctor_bot,
    title='医疗问诊机器人',
    chatbot=gr.Chatbot(height=400, bubble_full_width=False),
    theme=gr.themes.Default(spacing_size='sm', radius_size='sm'),
    textbox=gr.Textbox(placeholder="在此输入您的问题", container=False, scale=7),
    examples=[
        '你好，你叫什么名字？',
        '寻医问药网在哪一年成立的？',
        '寻医问药网的客服电话是多少？',
        '糖尿病是一种什么病？',
        '一般会有哪些症状？',
        '吃什么药好得快？可以吃阿莫西林吗？',
        '周杰伦最近有什么新专辑？',
        '塞外小清华是哪里?',
    ],
    submit_btn=gr.Button('提交', variant='primary'),
    clear_btn=gr.Button('清空记录'),
    retry_btn=None,
    undo_btn=None,
)

if __name__ == '__main__':
    demo.launch(share=False)