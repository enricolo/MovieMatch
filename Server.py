from flask import Flask
from flask_restful import Api, Resource, reqparse
import Database

app = Flask(__name__)
api = Api(app)

# Database.drop_users()
Database.createapproved_list()
Database.user_catalogo()

approvedFilmsArgs = reqparse.RequestParser()
approvedFilmsArgs.add_argument("id1", type=str, help="name of user", required=True)
approvedFilmsArgs.add_argument("id2", type=str, help="name of user2", required=True)
approvedFilmsArgs.add_argument("movie", type=str, help="name of movie", required=True)


usernameArgs = reqparse.RequestParser()
usernameArgs.add_argument("id1", type=str, required=True)

username2Args = reqparse.RequestParser()
username2Args.add_argument("id1", type=str, required=True)
username2Args.add_argument("id2", type=str, required=True)


#implement offset
class getFilm(Resource):#TODO restituisci dei film diversi 
    def get(self):
        movie = Database.get_top()[0]
        
        response = [{
            "Title": movie[0],
            "Poster": movie[1],
            "Year": movie[2]
            }]
        return response



class approvedFilms(Resource): 
    def post(self):
        args = approvedFilmsArgs.parse_args()
        print(args)
        
        user = args['id1']
        movie = args['movie']
        
        Database.insert_approvedfilm(user, movie)

        return {"res" : "msg"}
    
class checkFilms(Resource):
    def get(self): 
        args = username2Args.parse_args()
        print(args)
        
        commonmovies = Database.get_find_match(args['id1'], args['id2'])
                
        print(commonmovies)

                
        response = []
        if commonmovies:
            for movie in commonmovies:
                response.append({
                    "Title":            movie[0],
                    "TitleIta":         movie[1],
                    "Year":             movie[2],
                    "Image":            movie[3],
                    "Genre":            movie[4],
                    "AudienceRating":   movie[5],
                    "Studio":           movie[6],
                    "Plot":             movie[7]
                    })
            Database.remove_user_list(args['id1'])
            Database.remove_user_list(args['id2'])
            return {"res" : response}, 200
        
        else:
            return {"res" : "false"}, 404
        

            
class searchUser(Resource): 
    def get(self): 
        args = username2Args.parse_args()
        print(args)
        if(args['id1'] == args['id2']):   #check if not null
            return {"res" : "same name"}, 404
        user = Database.get_user(args['id2'])
        print(user)
        if user:
            return {"res" : "found"}, 200
        else:
            return {"res" : "not exists"}, 404
        # return {"res" : "found"}, 200
        
    
    
class userLogin(Resource):
    def post(self):
        args = usernameArgs.parse_args()
        print(args)
        print(args['id1'])
        
        Database.insert_user(args['id1'])
        Database.remove_user_list(args['id1'])
        return {"res" : "ok"}, 201
    
    
api.add_resource(userLogin, "/userLogin/")
api.add_resource(searchUser, "/searchUser/")
api.add_resource(getFilm, "/getfilm")
api.add_resource(approvedFilms, "/approvedFilms/")
api.add_resource(checkFilms, "/checkFilms/")


        

if __name__ == "__main__":
    # app.run(debug=True,port=10010) #need to call localhost 
    app.run(debug=True, host= '0.0.0.0',port=10010) #need to call serverip      if timeout reached check serverip
