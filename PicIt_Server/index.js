var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var http = require('http');
var path = require('path');
var fs = require('fs');
var multiparty = require('multiparty');
var mysql = require('mysql');
var con = mysql.createConnection({
    host: "localhost",
    port: "3306",
    user: "root",
    password: "test",
    database: "picit"
});

app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(bodyParser.json());

var rel_products = './products/';
var rel_shops = './shops/';
var rel_pictures = './pictures';

app.post("/adduser", function(req, res) {
    con.connect(function(err) {
        //NEED TO MAKE USERID NULLABLE
        con.query("insert into user values (null)", function(err, rows) {
            return res.send(rows.insertId);
        });
    });
});

app.get('/getHistory/:userid', function(req, res) {
    con.connect(function(err) {
        con.query("select * from picture where User_UserId = ? order by Time desc", [req.params.id] , function(err, rows) {
            console.log(rows);
            res.sendStatus(200).send(JSON.stringify(rows));
        });
    });
});

app.post('/identifyProduct', function(req, res) {
    //BODY KEYS:
    //userid: id
    //image : image
    var form = new multiparty.Form();
    form.parse(req, function(err, fields, files) {
        if (err) {
            throw err;
            return;
        }
        if (fields.id == undefined) {
            return res.sendStatus(400).end();
        }
        con.connect(function(err) {
            var oldpath = files.image[0].path;
            var randomdigits = crypto.randomBytes(2).toString('hex'); //avoiding pic name duplicates
            var filename = randomdigits + "_" + Date.now() + "." + files.image[0].originalFilename.split(".").pop(); //last pop is to get the extension
            console.log("After moving file is: " + filename);
            var newpath = rel_pictures + filename;
            fs.rename(oldpath, newpath, function(err) {
                if (err) {
                    throw err;
                    return;
                }
                con.query("insert into picture values (?, ?, ?)", [null, , fields.userid], function(err, rows) {
                    //hardcoded picture to product map
                    var picid = rows.insertId;
                    con.query("insert into picture_depicts_product values (?,?)", [null, 1], function(err, rows) {
                        con.query("select * from product where productId = ?", [1], function(err, rows) {
                            return res.status(200).send(rows);
                        });
                    });
                    return res.sendStatus(201).end();
                });
            });
        });
    });
});

app.get('/findStores/:prodId', function(req, res) {
    con.connect(function(err) {
        con.query("select * from shops s where s.StoreId in (select shp.store_storeid from store_has_product shp where shp.product_productid = ?)", [req.params.prodId], function(err, rows) {
            return res.sendStatus(200).send(rows);
        });
    });
});

app.get('store/:id/logo', function(req, res) {
    con.connect(function(err) {
        con.query("select logo from store where storeid = ?", [req.params.id], function(err, res) {
            if (rows.length == 0) return res.status(400).end();
            var filepath = rows[0].logo;
            res.sendFile(path.resolve(rel_shops + filepath));
        });
    });
});

http.createServer(app).listen(3003, function(err) {
    console.log("HTTP server listening...");
});