var express = require('express');
var router = express.Router();
var mongojs = require('mongojs');
var db = mongojs('mongodb://manish8025:manish8025@ds145563.mlab.com:45563/myweightsensor',['productData','sensorData']);

// Get All Tasks
router.get('/inventory', function(req, res, next){
    db.productData.find(function(err, sensordata){
        if(err){
            res.send(err);
        }
        res.json(sensordata);
    });
});

// Get Single Task
router.get('/inventory/:id', function(req, res, next){
    db.sensorData.findOne({_id: mongojs.ObjectId(req.params.id)}, function(err, sensordata){
        if(err){
            res.send(err);
        }
        res.json(sensordata);
    });
});

router.get('/sensor/:id', function(req, res, next){
    db.sensorData.findOne({_id: mongojs.ObjectId(req.params.id)}, function(err, sensordata){
        if(err){
            res.send(err);
        }   
        console.log(sensordata);
        res.json(sensordata);
    });
});

//Save Task
router.post('/inventory', function(req, res, next){
    var task = req.body;
    if(!task.title || !(task.isDone + '')){
        res.status(400);
        res.json({
            "error": "Bad Data"
        });
    } else {
        db.sensordata.save(task, function(err, task){
            if(err){
                res.send(err);
            }
            res.json(task);
        });
    }
});

// Delete Task
router.delete('/inventory/:id', function(req, res, next){
    db.sensordata.remove({_id: mongojs.ObjectId(req.params.id)}, function(err, sensordata){
        if(err){
            res.send(err);
        }
        res.json(sensordata);
    });
});

// Update Task
router.put('/inventory/:id', function(req, res, next){
    var task = req.body;
    var updTask = {};
    
    if(task.isDone){
        updTask.isDone = task.isDone;
    }
    
    if(task.title){
        updTask.title = task.title;
    }
    
    if(!updTask){
        res.status(400);
        res.json({
            "error":"Bad Data"
        });
    } else {
        db.sensordata.update({_id: mongojs.ObjectId(req.params.id)},updTask, {}, function(err, sensordata){
        if(err){
            res.send(err);
        }
        res.json(sensordata);
    });
    }
});

module.exports = router;
