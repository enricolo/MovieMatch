import sqlite3   
import time
 
def absolutePath():
    return "/home/enricolo/Documents/VisualStudioCode/PlexApiTest/"

def create_catalogo():
    conn = sqlite3.connect(absolutePath()+'database.db')

    c = conn.cursor()
    #da testare unicità chiave (url + name)
    c.execute("""CREATE TABLE IF NOT EXISTS catalogo (
    title TEXT NOT NULL COLLATE NOCASE,
    titleIta TEXT,
    year INTEGER NOT NULL,
    image TEXT,
    genre TEXT,   
    audienceRating REAL,
    studio TEXT,
    plot TEXT, 
    imdbid TEXT
    )""")


def insert_data(title, titleIta, year, image, genre, audienceRating, studio, plot, imdbid):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:

        print(">>>>>>>>>NEW ELEMENT")
        #element is new, 
        c.execute("""INSERT INTO catalogo VALUES (:title, :titleIta, :year, :image, :genre, :audienceRating, :studio, :plot, :imdbid)""", 
        {'title':title,'titleIta':titleIta, 'year':year, 'image':image, 'genre':genre, 'audienceRating':audienceRating,'studio':studio, 'plot':plot, 'imdbid':imdbid})


def get_alldata():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT * FROM catalogo ORDER BY audienceRating DESC""")
    return c.fetchall()

def get_alltitle():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT title, year FROM catalogo ORDER BY audienceRating DESC""")
    return c.fetchall()

def get_top():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT title, image, year FROM catalogo ORDER BY random() LIMIT 1""") #
    return c.fetchall()


def removetutto():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("""DELETE from catalogo""")

        
def update_info(title, image, genre, plot, studio):
    
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("""UPDATE catalogo SET image=:image, genre=:genre, plot=:plot, studio=:studio WHERE title = :title COLLATE NOCASE""",
            {'title':title, 'image':image, 'genre':genre, 'plot':plot, 'studio':studio})

##############################################################################
#                             PLATFORM TABLE
##############################################################################   


def create_platform_catalogo():
    conn = sqlite3.connect(absolutePath()+'database.db')

    c = conn.cursor()
    #da testare unicità chiave (url + name)
    c.execute("""CREATE TABLE IF NOT EXISTS platforms (
    title TEXT NOT NULL COLLATE NOCASE,
    year INTEGER NOT NULL, 
    imdbid TEXT,
    platform TEXT
    )""")
    
def insert_film(title, year,imdbid, platform):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        # print(title)
        c.execute("""INSERT OR REPLACE INTO platforms VALUES (:title, :year, :imdbid, :platform)""", 
        {'title':title,'year':year,'imdbid':imdbid,'platform':platform})
 
def check_if_new(title, year,imdbid):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()        
    c.execute("""SELECT * 
        FROM platforms as p 
        WHERE exist (
            SELECT 1 
            FROM catalogo as c
            WHERE c.title = p.title AND c.year = p.year AND c.title = :title AND c.year = :year)""", 
        {'title':title,'year':year,'imdbid':imdbid})
    
    return c.fetchall()

def removeplatforms():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("""DELETE from platforms""")
                
def drop_platforms():
    conn = sqlite3.connect(absolutePath()+'database.db')
    conn.execute("DROP TABLE platforms")
    conn.commit()            
        

##############################################################################
#                             USER TABLE
##############################################################################


    
def user_catalogo():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    #da testare unicità chiave (url + name)
    c.execute("""CREATE TABLE IF NOT EXISTS users (
    user TEXT NOT NULL PRIMARY KEY,
    password TEXT,
    altro TEXT
    )""")
    
def insert_user(user): #TODO 2 concurrent username might be a problem
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        
        #element is new, 
        c.execute("""INSERT OR REPLACE INTO users VALUES (:user , :password, :altro)""", 
        {'user':user, 'password':'', 'altro':''})
        

def get_user(user):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT * FROM users WHERE user = :user""",
            {'user':user})
    return c.fetchall()


def removeuser(user):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("""DELETE from catalogo WHERE user = :user""")
        
def drop_users():
    conn = sqlite3.connect(absolutePath()+'database.db')
    conn.execute("DROP TABLE users")
    conn.commit()
        
##############################################################################
#                             approved_list
##############################################################################        
    

def createapproved_list():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    #da testare unicità chiave (url + name)
    c.execute("""CREATE TABLE IF NOT EXISTS approvedmovies (
    user TEXT NOT NULL,
    title TEXT NOT NULL,
    year INTEGER
    )""")
    

def insert_approvedfilm(user, movie): #TODO 2 concurrent username might be a problem
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        
        c.execute("""INSERT INTO approvedmovies VALUES (:user , :title, :year)""", 
        {'user':user, 'title':movie, 'year':""})
        
        
def get_userfilms(user):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT title, year FROM approvedmovies WHERE user = :user""",
            {'user':user})
    return c.fetchall()


def get_find_match(user1,user2):#TODO solve problem with films with same title
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT *
                FROM catalogo as c
                WHERE c.title in (              
                    SELECT am1.title
                    from approvedmovies as am1
                    where am1.user = :user1 and am1.title in (
                        SELECT am2.title
                        from approvedmovies as am2
                        where am2.user = :user2))""",
                {'user1':user1, 'user2':user2 })
    return c.fetchall()
    
def remove_user_list(user):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("""DELETE from approvedmovies WHERE user = :user""",
            {'user':user})

        
def remove_approved_film(user, title):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("""DELETE from approvedmovies WHERE user = :user and title = :title""",
            {'user':user, 'title':title})
    
##############################################################################
#                             loading_table
##############################################################################   

def create_settings():
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    
    c.execute("""CREATE TABLE IF NOT EXISTS settings (
    name_table TEXT,
    platform_common TEXT,
    genres_common TEXT,
    setoffset INTEGER,
    setlimit INTEGER,
    creationdate INTEGER
    )""")
    

#FIRST insertion of information of the first user
def insert_setting(user1, user2, platforms, genres):
    if get_settings(user1, user2) is not None:
        change_settings(user1, user2, platforms, genres)
    else:
        userA,userB = sortusers(user1, user2)
        conn = sqlite3.connect(absolutePath()+'database.db')
        c = conn.cursor()
        with conn:
            
            c.execute("""INSERT INTO settings (name_table, platform_common, genres_common) 
                    VALUES (:name_table, :platform_common , :genres)""", 
            {'name_table':userA+userB,'platform_common':platforms, 'genres':genres})
    
        

def change_settings(user1, user2, platforms, genres):
    res = get_settings(user1, user2)
    print(res)
    platform_common = set(res[1].split(", "))
    genres_common = set(res[2].split(", "))
    platforms = set(platforms.split(", "))
    genres = set(genres.split(", "))
    
    platforms = platform_common & platforms
    genres = genres_common.union(genres) 
    
    platform_common = ""
    genres_common = ""
    
    for p in platforms:
        platform_common += p + ", "
    for g in genres:
        genres_common += g + ", "
    
    userA,userB = sortusers(user1, user2)
    
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("UPDATE settings SET platform_common = :platform_common, genres_common = :genres_common, setoffset = :setoffset, setlimit = :setlimit, creationdate = :creationdate WHERE name_table = :name_table", 
        {'name_table':userA+userB,'platform_common':platform_common, 'genres_common':genres_common, 'setoffset':0, 'setlimit':100, 'creationdate':int(time.time()/3600)})


def get_settings(user1, user2):
    userA,userB = sortusers(user1, user2)
    
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("""SELECT * FROM settings WHERE name_table = :name_table""", 
        {'name_table':userA+userB})
    return c.fetchone()

def is_row_complete(user1, user2):
    userA,userB = sortusers(user1, user2)
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    
    c.execute("""SELECT 1 FROM settings WHERE name_table = :name_table""", 
    {'name_table':userA+userB})
    
    if c.fetchone():
        create_users_table(user1, user2)
        return 1
    else:
        return None


def drop_loading_list(user1, user2):
    conn = sqlite3.connect(absolutePath()+'database.db')
    conn.execute("DROP TABLE settings")
    conn.commit()

############################################################################## testo += ""
#                             User movie table
##############################################################################   

def create_users_table(user1, user2):
    userA,userB = sortusers(user1, user2)
    conn = sqlite3.connect(absolutePath()+'database.db')

    testo = "CREATE TABLE IF NOT EXISTS "+userA+userB+" ("
    testo += "title TEXT NOT NULL COLLATE NOCASE,"
    testo += "year INTEGER NOT NULL, "
    testo += "imdbid TEXT, "
    testo += userA + " TEXT, "
    testo += userB + " TEXT)"    

    c = conn.cursor()
    c.execute(testo)

def populate_user_movies(user1, user2): #TODO doesn't consider streaming platform
    userA,userB = sortusers(user1, user2)
    res = get_settings(user1, user2)
    
    platform = res[1].split(", ")
    genres = res[2].split(", ")
    offset = res[3]
    
    
    testo ="INSERT INTO "+userA+userB + " (title, year, imdbid) " 
    testo += "SELECT title, year, \"\" " 
    testo += "FROM catalogo "
    testo += "WHERE "
    for genre in genres:
        if genre == "":
            continue
        testo += "genre not like \"%"+genre+"%\" AND " 
    testo += "genre != \"\" "
    testo += "ORDER BY audienceRating DESC Limit 100 OFFSET "+str(offset)
    
    print (testo)
    
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute(testo)
    
    
def set_user_approved(user1, user2, title):
    userA,userB = sortusers(user1, user2)
    
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        c.execute("UPDATE "+userA+userB+" SET "+user1+"="+str(1)+" WHERE title = \""+title+"\" COLLATE NOCASE")
        
#return a random movie from the movie list of the user       
def get_user_film(user1, user2): 
    userA,userB = sortusers(user1, user2)
    
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    c.execute("SELECT * FROM catalogo as c WHERE c.title in (SELECT A.title FROM "+userA+userB+" as A WHERE "+user1+" IS NULL ORDER BY random() LIMIT 1 )")
    res = c.fetchone()
    if res is not None:
        print(res[0])
        with conn:
            c.execute("UPDATE "+userA+userB+" SET "+user1+"=\"?\" WHERE title = \""+res[0]+"\"")
        return res
    else:
        populate_user_movies(user1, user2)
        #TODO change offset by 100



def drop_user_movie_table(user1, user2):
    userA,userB = sortusers(user1, user2)
    conn = sqlite3.connect(absolutePath()+'database.db')
    conn.execute("DROP TABLE "+userA+userB)
    conn.commit()

##############################################################################
#                             sortmethod
##############################################################################   

def sortusers(user1, user2):
    if(user1 < user2):
        return user1, user2
    
    return user2, user1
    