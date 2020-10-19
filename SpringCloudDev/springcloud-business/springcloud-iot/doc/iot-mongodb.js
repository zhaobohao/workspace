

db = db.getSiblingDB('dc3');

if (!db.getUser("dc3")) {
    db.createUser({
        user: "dc3",
        pwd: "dc3",
        roles: [{
            role: "readWrite",
            db: "dc3"
        }]
    })
}

if (!db.getCollection("pointValue")) {
    db.createCollection("pointValue");
    db.pointValue.createIndex({
        "deviceId": 1,
        "pointId": 1
    }, {unique: false});
}


