import json
import pymysql
import re
from flask import request, app, Flask
from flask_sqlalchemy import SQLAlchemy
from flask_httpauth import HTTPBasicAuth
from flask_bcrypt import Bcrypt

app = Flask(__name__)

bcrypt = Bcrypt(app)
# app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:1qaz2wsx@localhost/sys'
# app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
auth = HTTPBasicAuth()
conn = pymysql.connect(host='0.0.0.0', user='root', password='1qaz2wsx', db='sys',
                       cursorclass=pymysql.cursors.DictCursor)
db = conn.cursor()
url = "http://127.0.0.1:8080/"

users = {
    "admin": "admin"
}


@auth.verify_password
def verify_password(username, password):
    if username in users and password == users[username]:
        return username


@app.route('/v1/user', methods=['GET', 'PUT'])
@auth.login_required
def index():
    userID = request.args.get('id')
    sql = "SELECT id,first_name,last_name,username, date_format(account_created,'%%Y-%%m-%%d'),date_format(account_updated,'%%Y-%%m-%%d') from user where id=%s;"
    db.execute(sql, [userID])
    result = db.fetchall()
    if request.method == 'GET':
        if result:
            response = json.dumps(result)
            return response, 201
        else:
            return 'forbidden', 403
    elif request.method == 'PUT':
        data = request.get_json()
        if not data:
            return 'no content', 201
        if data.get('id') or data.get('username') or data.get('account_created') or data.get('account_updated'):
            return 'Bad Request', 400
        if not result:
            return 'forbidden', 403
        if data.get('password'):
            sql = 'UPDATE `User` SET password = %s WHERE id=%s;'
            hashPassword = bcrypt.generate_password_hash(data.get('password'))
            db.execute(sql, [hashPassword, userID])
        if data.get('first_name'):
            sql = 'UPDATE `User` SET first_name = %s WHERE id=%s;'
            db.execute(sql, [data.get('first_name'), userID])
        if data.get('last_name'):
            print(data.get('last_name'))
            sql = 'UPDATE `User` SET last_name = %s WHERE id=%s;'
            db.execute(sql, [data.get('last_name'), userID])
        conn.commit()
        return "Hello, {}!".format(auth.current_user())


@app.route('/healthz', methods=['GET'])  # 代表个人中心页
def get():  # 视图函数
    return 'healthy', 200


@app.route('/', methods=['POST'])  # 代表个人中心页
def create():  # 视图函数
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')
    hashPassword = bcrypt.generate_password_hash(password, 10)
    firstName = data.get('first_name')
    lastName = data.get('last_name')
    sql = "select * from User where username=%s;"
    # 拼接并执行SQL语句
    db.execute(sql, [username])
    results = db.fetchall()
    if results or not re.match("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$", username):
        return 'Bad Request', 400
    else:
        sql = 'insert into User (first_name,last_name,username,password) values (%s,%s,%s,%s);'
        db.execute(sql, [firstName, lastName, username, hashPassword])
        sql = "SELECT id,first_name,last_name,username, date_format(account_created,'%%Y-%%m-%%d'), date_format(account_updated,'%%Y-%%m-%%d') from user where username=%s;"
        db.execute(sql, [username])
        result = db.fetchall()
        response = json.dumps(result)
        conn.commit()
        return response, 201


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)  # 运行程序
