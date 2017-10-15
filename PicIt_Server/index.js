var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var http = require('http');
var path = require('path');
var fs = require('fs');
var dateTime = require('node-datetime');
var multiparty = require('multiparty');
var mysql = require('mysql');
var crypto = require('crypto');
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
var rel_pictures = './pictures/';

app.post("/adduser", function(req, res) {
    con.connect(function(err) {
        con.query("insert into user values ()", function(err, rows) {
            console.log("New user created");
            return res.send(rows.insertId.toString());
        });
    });
});

app.get('/getHistory/:userid', function(req, res) {
    con.connect(function(err) {
        con.query("select pi.pictureid, pi.time, pr.* from picture pi, product pr, picture_depicts_product pdp where pi.user_userid = ? and pdp.picture_pictureid = pi.pictureid and pr.productid = pdp.product_productid order by pi.time desc", [req.params.userid], function(err, rows) {
            return res.send(rows);
        });
    });
});

app.get('/picture/:picid', function(req, res) {
    con.connect(function(err) {
        con.query("select picturename from picture where pictureid = ?", [req.params.picid.toString()], function(err, rows) {
            if (rows.length == 0) return res.status(400).end();
            var filepath = rows[0].picturename;
            res.sendFile(path.resolve(rel_pictures + filepath));
        });
    });
});

app.post('/identifyProduct', function(req, res) {
    //BODY KEYS:
    //userid: id
    //image : image
    var form = new multiparty.Form();
    form.parse(req, function(err, fields, files) {
        console.log(files);
        console.log(fields);
        if (err) {
            throw err;
            return;
        }
        if (fields.userid == undefined) {
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
                var dt = dateTime.create();
                var formatted = dt.format('Y-m-d H:M:S');
                con.query("insert into picture values (?, ?, ?, ?)", [null, formatted, fields.userid, filename], function(err, rows) {
                    //hardcoded picture to product map
                    var picid = rows.insertId.toString();
                    con.query("insert into picture_depicts_product values (?,?)", [picid, 1], function(err, rows) {
                        con.query("select * from product where productId = ?", [1], function(err, rows) {
                            return res.send(rows[0]).end();
                        });
                    });
                });
            });
        });
    });
});

app.get('/findStores/:prodId', function(req, res) {
    con.connect(function(err) {
        con.query("select shp.store_storeid, shp.price, shp.description, s.name from store_has_product shp, store s where shp.product_productid = ? and s.storeid = shp.store_storeid", [req.params.prodId], function(err, rows) {
            return res.send(rows);
        });
    });
});

app.get('/store/:id', function(req, res) {
    con.connect(function(err) {
        con.query("select * from store where storeid = ?", [req.params.id], function(err, rows) {
            return res.send(rows[0]);
        })
    });
});

app.get('store/:id/logo', function(req, res) {
    con.connect(function(err) {
        con.query("select logo from store where storeid = ?", [req.params.id], function(err, rows) {
            if (rows.length == 0) return res.status(400).end();
            var filepath = rows[0].logo;
            res.sendFile(path.resolve(rel_shops + filepath));
        });
    });
});

app.get('/findSimilarProducts/:productid', function(req, res){
    con.connect(function(err){
        con.query("select ProductId,ProductName from product,product_has_tags pht1 where ProductId = Product_ProductId and ProductId != ? " +
            "and not exists(select Tags_TagId from product_has_tags pht2 where not exists(select * from product_has_tags pht3 where ProductId != pht3.Product_ProductId and " +
            "pht3.Tags_TagId = pht2.Tags_TagId)", [req.params.productid] , function(err, rows){
            console.log(rows);
            res.sendStatus(200).send(JSON.stringify(rows));
        });
    });
});

http.createServer(app).listen(3003, function(err) {
    console.log("HTTP server listening...");
});