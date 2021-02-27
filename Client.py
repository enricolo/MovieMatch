import requests

#BASE = "http://127.0.0.1:10010/"  #localhost

BASE = "http://192.168.1.100:10010/" #serverip


# approvedFilmsArgs.add_argument("id1", type=str, required=True)
# approvedFilmsArgs.add_argument("id2", type=str, required=True)
# approvedFilmsArgs.add_argument("movie", type=str, required=True)
# api.add_resource(approvedFilms, "/userLogin/<string:login>")
# api.add_resource(approvedFilms, "/searchUser/<string:user>")
# api.add_resource(getFilm, "/getfilm")
# api.add_resource(approvedFilms, "/approvedFilms/<string:approved>")


print('username')
username = input()
print(BASE + "userLogin/",{"id1":username})
responds = requests.post(BASE + "userLogin/",{"id1":username})

responds = responds.json()
print (responds)

while(True):
    print('username seconda persona')
    username2 = input()
    print(BASE + "searchUser/",{"id1":username, "id2":username2})
    responds = requests.get(BASE + "searchUser/",{"id1":username, "id2":username2})
    responds = responds.json()
    print (responds)
    if(responds["res"]== 'found'):
        break

while responds != "MATCH!!!":
    print('ricevi film')
    responds = requests.get(BASE + "getfilm")
    responds = responds.json()
    print (responds)
    
    print('dammi film che ti piace')
    responds = requests.post(BASE + "approvedFilms/",{"id1":username, "id2":username2, "movie":input()})
    responds = responds.json()
    print (responds)
    
    print('send check')
    username = 'marco'
    username2 = 'enrico'
    responds = requests.get(BASE + "checkFilms/",{"id1":username, "id2":username2})
    responds = responds.json()
    print (responds)