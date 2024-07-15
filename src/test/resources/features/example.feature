#language:es
Característica: Automatización de API Reqres

  @GET @LIST_USER
  Escenario: Obtener lista de usuarios
    Dado que configuro las cabeceras
      | cabecera     | valor            |
      | Content-type | application/json |
    Y configuro los query params
      | key  | valor |
      | page | 2     |
    Cuando ejecuto el api
      | api       | endpoint   | method |
      | ux-reqres | /api/users | GET    |
    Entonces valido que el status code sea "200"
    Y verifico la respuesta del servicio
      | nodo    | valor |
      | user.id | 0001  |
    Y valido el esquema de la respuesta "lista-usuarios.json"

  @POST @CREATE_USER
  Escenario: Crear usuario
    Dado que configuro las cabeceras
      | cabecera     | valor            |
      | Content-type | application/json |
    Y configuro el body request "usuario.json"
      | key  | valor |
      | page | 2     |
    Cuando ejecuto el api
      | api       | endpoint   | method |
      | ux-reqres | /api/users | POST   |
    Entonces valido que el status code sea "200"
    Y verifico la respuesta del servicio
      | nodo    | valor |
      | user.id | 0001  |
    Y valido el esquema de la respuesta "usuario.json"

  @PUT @UPDATE_USER
  Escenario: Actualizar usuario mediante id
    Dado que configuro las cabeceras
      | cabecera     | valor            |
      | Content-type | application/json |
    Y configuro los paths variables
      | key    | valor |
      | idUser | 2     |
    Y configuro el body request "usuario.json"
      | key  | valor |
      | page | 2     |
    Cuando ejecuto el api
      | api       | endpoint            | method |
      | ux-reqres | /api/users/{idUser} | PUT    |
    Entonces valido que el status code sea "200"
    Y verifico la respuesta del servicio
      | nodo    | valor |
      | user.id | 0001  |
    Y valido el esquema de la respuesta "usuario.json"

  @DELETE @DELETE_USER
  Escenario: Eliminar usuario por id
    Dado que configuro las cabeceras
      | cabecera     | valor            |
      | Content-type | application/json |
    Y configuro los paths variables
      | key    | valor |
      | idUser | 2     |
    Cuando ejecuto el api
      | api       | endpoint            | method |
      | ux-reqres | /api/users/{idUser} | DELETE |
    Entonces valido que el status code sea "204"
    Y verifico la respuesta del servicio
      | nodo    | valor |
      | user.id | 0001  |
    Y valido el esquema de la respuesta "lista-usuarios.json"
