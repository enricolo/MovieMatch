import sqlite3   
 
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
    plot TEXT
    )""")


def insert_data(title, titleIta, year, image, genre, audienceRating, studio, plot):
    conn = sqlite3.connect(absolutePath()+'database.db')
    c = conn.cursor()
    with conn:
        # try:
            print(">>>>>>>>>NEW ELEMENT")
            #element is new, 
            c.execute("""INSERT INTO catalogo VALUES (:title, :titleIta, :year, :image, :genre, :audienceRating, :studio, :plot)""", 
            {'title':title,'titleIta':titleIta, 'year':year, 'image':image, 'genre':genre, 'audienceRating':audienceRating,'studio':studio, 'plot':plot})
        # except sqlite3.IntegrityError as e:
        #     pass



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
    c.execute("""SELECT title, image, year FROM catalogo ORDER BY random() LIMIT 1""")
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
#                             USER TABLE
##############################################################################

def drop_users():
    conn = sqlite3.connect(absolutePath()+'database.db')
    conn.execute("DROP TABLE users")
    conn.commit()
    
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
        c.execute("""INSERT OR REPLACE INTO users VALUES (:user , :title, :year)""", 
        {'user':user, 'title':'', 'year':''})
        

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


def get_find_match(user1,user2):
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
    
    
    