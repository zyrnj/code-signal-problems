import pytest
import User

@pytest.fixture()
# 定义一个测试数据
def data():
    return 1

@pytest.fixture()
def client():
    flask_app = User.app
    # Flask provides a way to test your application by exposing the Werkzeug test Client
    # and handling the context locals for you.
    client = flask_app.test_client()
    # Establish an application context before running the tests.
    ctx = flask_app.app_context()
    ctx.push()
    yield client  # this is where the testing happens!
    ctx.pop()

def test_pass(data):
    assert 1 == data

def test_createuser_notemail(client):
  response = client.post('/',data={
  "first_name": "Jane",
  "last_name": "Doe",
  "password": "somepassword",
  "username": "jane.doeexample.com"
})
  assert response.status_code == 400

def test_home_page(client):
  response = client.get('/healthz')
  assert response.status_code == 200


if __name__ == '__main__':
    pytest.main(["-q", "test_pass.py"])