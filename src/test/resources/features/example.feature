#language:es
Característica: Automatización de API Reqres
  #https://reqres.in/

  @GET @LIST_USER
  Escenario: Obtener lista de usuarios
    Dado que configuro los headers
      | header       | value            |
      | Content-type | application/json |
    Y configuro los query params
      | key  | value |
      | page | 2     |
    Cuando ejecuto el api
      | api       | endpoint   | method |
      | ux-reqres | /api/users | GET    |
    Entonces valido que el status code sea "200"
    Y verifico la respuesta del servicio
      | node | value |
      | page | 2     |
    Y valido el esquema de la respuesta "list-schema.json"

  @POST @CREATE_USER
  Escenario: Crear usuario
    Dado que configuro los headers
      | header       | value            |
      | Content-type | application/json |
    Y configuro el body request "usuario.json"
      | key  | value |
      | name | Name  |
      | job  | QA    |
    Cuando ejecuto el api
      | api       | endpoint   | method |
      | ux-reqres | /api/users | POST   |
    Entonces valido que el status code sea "201"
    Y verifico la respuesta del servicio
      | node | value |
      | name | Name  |
    Y valido el esquema de la respuesta "usuario-schema.json"

  @PUT @UPDATE_USER
  Escenario: Actualizar usuario mediante id
    Dado que configuro los headers
      | header       | value            |
      | Content-type | application/json |
    Y configuro los paths variables
      | key    | value |
      | idUser | 2     |
    Y configuro el body request "usuario.json"
      | key  | value       |
      | name | Name Update |
      | job  | QA          |
    Cuando ejecuto el api
      | api       | endpoint            | method |
      | ux-reqres | /api/users/{idUser} | PUT    |
    Entonces valido que el status code sea "200"
    Y verifico la respuesta del servicio
      | node | value       |
      | name | Name Update |
    Y valido el esquema de la respuesta "usuario-schema.json"

  @DELETE @DELETE_USER
  Escenario: Eliminar usuario por id
    Dado que configuro los headers
      | header       | value            |
      | Content-type | application/json |
    Y configuro los paths variables
      | key    | value |
      | idUser | 2     |
    Cuando ejecuto el api
      | api       | endpoint            | method |
      | ux-reqres | /api/users/{idUser} | DELETE |
    Entonces valido que el status code sea "204"


  @CREATE_DATA @EXCEL
  Escenario: Crear usuarios mediante excel
    Dado que configuro los headers
      | header       | value            |
      | Content-type | application/json |
    Cuando ejecuto el api y configuro el body desde un excel "/data/data-user.xlsx"
      | api       | endpoint   | method | nameSheet |
      | ux-reqres | /api/users | POST   | users     |
    Entonces valido que el status code de las peticiones sea "201"