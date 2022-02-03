# Socket

Sviluppare un'applicazione java distribuita su due JVM tale che:

Sulla prima JVM gira un server che si mette in attesa di richieste da uno o più client. Ad ogni richiesta il server deve ricevere dal client una lista di oggetti di tipo Studente (la classe Studente ha variabili di istanza "nome" e "descrizione", con relativi metodi getter e costruttore); il server visualizza gli studenti della lista sulla propria finestra e ne modifica il campo "descrizione". Poi il server rispedisce al client la lista di studenti modificata e chiude la connessione.
Il client contatta il server richiedendo una connessione via socket e spedisce una lista di oggetti Studente (come sopra). Poi si mette in attesa della lista di Studenti modificata e la visualizza sulla propria finestra. Infine chiude la connessione con il server.
HELP: il server usi un SocketServer e crei i socket, utilizzando inputStream e outputStream. I client si connettano al server su IP address e porta da esso specificati e aprano socket, recuperando input e output stream per ricevere e inviare i dati. Al termine delle operazioni si chiudano gli stream e i socket.
