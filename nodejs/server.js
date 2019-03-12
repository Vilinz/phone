/*
let express = require('express');
let pg = require('pg')
let bodyParser = require('body-parser');

const pgConfig = {
    user: 'postgres',           // 数据库用户名
    database: 'testdb',       // 数据库
    password: '111111',       // 数据库密码
    host: '127.0.0.1',        // 数据库所在IP
    port: '5432'                // 连接端口
};

const pool = new pg.Pool(pgConfig);

let app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

pool.connect(function(error, client, done) {

  app.post('/login',(request,response,next)=>{
    let post_data = request.body;
    let username = post_data.username;
    let password = post_data.password;
    console.log(username);
    let queryPassword;
    let sqlStr = 'SELECT password, phone FROM phone WHERE username = ' + '\'' + username + '\'';      // 查表的SQL语句
    client.query(sqlStr).then(function(response1) {
      let msg;
      console.log(response1);
      if((response1 == undefined)||(response1.rows.length == 0)) {
        msg = "user_not_exist";
      } else {
        console.log(response1.rows[0].password);    // 根据SQL语句查出的数据
        queryPassword = response1.rows[0].password;
        console.log(password + ' ' + queryPassword);
        if(password == queryPassword) {
          msg = "successfully";
        } else {
          msg = "password_error";
        }
      }
      response.json(msg);
    });
  });

  app.post('/register',(request,response,next)=>{
      let post_data = request.body;
      let username = post_data.username;
      let password = post_data.password;
      let phone = post_data.phone;

      let sqlStr = 'SELECT password, phone FROM phone WHERE username = ' + '\'' + username + '\'';      // 查表的SQL语句
      client.query(sqlStr).then(function(response1) {
        done();
        let msg;
        if((response1 == undefined)||(response1.rows.length == 0)) { //case username have not been used!
          let sqlStr = "INSERT INTO phone(username, password, phone) VALUES " + "(\'" + username + "\', \'" + password + "\', \'" + phone + "\');";
          client.query(sqlStr).then(function(response2) {
            done();
            msg = "register_successfully";
          });
         
        } else { //case username have been used!
          msg = "username_used";
        }
        response.json(msg);
      });
    });

  app.listen(3000,()=>{
      console.log('Connected to My Server, Webservice running on port 3000')
    });
})


CREATE TABLE phone(
    username text,
    password text,
    phone text,
    PRIMARY KEY(username)
);
*/
let mongodb = require('mongodb');
let express = require('express');
let bodyParser = require('body-parser');

let app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

let MongoClient = mongodb.MongoClient;

let url = "mongodb://localhost:27017";

MongoClient.connect(url, {useNewUrlParser: true}, function(err, client) {
  if(err) {
    console.log('error');
  } else {
    app.post('/login', (require, response, next) =>{
      let post_data = require.body;
      let username = post_data.username;
      let password = post_data.password;

      let db = client.db('local');
      db.collection('phone').find({'username':username}).count(function(err, number) { 
        if(number == 0) {
          response.json("user_not_exist");
        } else {
          db.collection('phone').findOne({'username':username}, function(err, user) {
            let query_password = user.password;
            console.log(query_password);
            if(password == query_password) {
              response.json("successfully");
            } else {
              response.json("password_error");
            }
          });
        }
      });
    });

    app.post('/register', (require, response, next) =>{
      let post_data = require.body;
      let username = post_data.username;
      let password = post_data.password;
      let phone = post_data.phone;

      let db = client.db('local');

      var insertJson = {
				'username': username,
				'password': password,
				'phone':phone
			};

      db.collection('phone').find({'username':username}).count(function(err, number) {
        if(number != 0) {
          response.json("username_used");
        } else {
          db.collection('phone').insertOne(insertJson, function(err, res) {
            if(err) {
              response.json("unknow_error");
            } else {
              response.json("register_successfully");
            }
          });
        }
      });
    });

    app.listen(3000,()=>{
      console.log('Connected to My Server, Webservice running on port 3000')
    });
  }
})







