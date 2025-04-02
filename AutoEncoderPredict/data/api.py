from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

# 读取风机数据
def load_data():
    return pd.read_csv('data/fan_data.csv')

@app.route('/get_data', methods=['GET'])
def get_data():
    df = load_data()
    return jsonify(df.head().to_dict())  # 返回前5行数据

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)  # 允许局域网访问