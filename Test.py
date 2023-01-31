import pytest

@pytest.fixture()
# 定义一个测试数据
def data():
    return 1


def test_pass(data):
    assert 1 == data

@pytest.mark.website
def test_1():
    print('Test_1 called.')

if __name__ == '__main__':
    pytest.main(["-q", "test_pass.py"])