meli-java-example
=================
Estoy empezando a incursionar en las API de Mercado Libre y decidí crear una aplicación de ejemplo. 
La idea es utilizar la sdk de Java disponible para Mercado Libre para poder hacerlo. También existen para otros lenguajes como PHP, Python, entre otros. En esta github de mercado libre podes encontrar más información.

En este ejemplo vamos a utilizar la sdk de java.

Los pasos son los siguentes:

1) Para poder conectarse con las API de MELI es necesario utilizar el Aplication Manager. En ese link van a encontrar information de como crear el App id y el Secret Key.

2) Una vez que tenemos el App id (clientID) y el Secret Key(clientSecret) vamos a bajar la sdk-Java de Mercado Libre.
Para proyectos maven solamente con agregar un nuevo repositorio alcanza.

    <repositories>
        ...
        <repository>
             <id>mercadolibre-snapshots</id>
             <url>https://github.com/mercadolibre/java-sdk-repo/raw/master/snapshots</url>
        </repository>
        ...
    </repositories>


Y después la dependencia

    <dependencies>
           ...
           <dependency>
                 <groupId>com.mercadolibre</groupId>
                 <artifactId>sdk</artifactId>
                 <version>0.0.2-SNAPSHOT</version>
          </dependency>
          ...
    </dependencies>



3) Una vez configurado su entorno vamos a proceder a realizar la comunicación con MELI.

Creamos una nueva clase llamada Site como sigue:

        public class Site {

            private final String id;
            private final String name;

            public Site(final String id, final String name) {
                this.id = id;
                this.name = name;
            }

            @Override
            public String toString() {
                return "Site{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }
        }


Esta clase va a contener toda la información de los Sites.

Luego creamos un SiteHandler para realizar las consultas.

    public class SiteHandler {
       //Nombre de los campos que vamos a obtener informacion
        private static final String ID_KEY = "id";
        private static final String NAME_KEY = "name";
        //Creamos una unica instancia de Meli.
        private final Meli meli;

        private static final SiteHandler instance = new SiteHandler();

        private SiteHandler() {
            //Aca creamos el objecto Meli con la información de su aplicación.
            meli = new Meli(clientId, clienteSecret);
        }

        public static SiteHandler getInstance() {
            return instance;
        }

        public List<Site> getAllMeliSite()  {
            //Todos los sites.
            final List<Site> newSites = new ArrayList<Site>();
            try {
                //Aqui realizamos la consulta de todos los sites.
                final Response response = meli.get("/sites/");
                //Objeto gson que sirve para "parsear" los resultados.
                final Gson gson = new Gson();
                //Convertimos desde JSON a Java creando una lista de StringMap
                final List<StringMap<String>> sites = gson.fromJson(response.getResponseBody(), List.class);
                //Convertimos los objectos StringMap a Site.
                for (final StringMap<String> entries : sites) {
                    newSites.add(new Site(entries.get(ID_KEY), entries.get(NAME_KEY)));
                }
            } catch (MeliException ex) {
                //Logger error en la respuesta
                System.out.println("Error " + ex.getMessage());
            } catch (IOException e) {
                //Logger error en la transformacion usando de gson.
                System.out.println("Error " + e.getMessage());
            }
            return newSites;
        }
    }


Por último creamos un objeto main para que ejecute lo anterior:

    public class Main {

        public static void main(String[] args) throws Exception {
            final List<Site> sites = SiteHandler.getInstance().getAllMeliSite();

            for (final Site site: sites) {
                System.out.println(site);
            }
        }   
    }


Espero que sirva este sensillo ejemplo.

http://migranitodesoftware.blogspot.com.ar/2014/11/meli-api-sdk-java-obtener-datos-con-una.html
