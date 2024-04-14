db = connect('mongodb://root:example@localhost:27018/proyecto-test?authSource=admin');

db.moderadores.insertMany([
    {
        _id:  '661aad765a17e523f53fb224',
        nombre: 'David',
        email: 'mode1@correo.com',
        password: '$2a$10$ikCsbqn4H/D12ZehmM541e69EvcClVSiCyrVZrzg42UhAgwIzFYAG',
        estadoRegistro: 'ACTIVO',
        rol: 'MODERADOR',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    },
    {
        _id: '661aad765a17e523f53fb225',
        nombre: 'Leonardo',
        mail: 'mode2@correo.com',
        password: '$2a$10$2Vb5bywTAx4Hoss688TGMeMX/7GGKOwesifLQgNYBlkFQSHYHKMHi',
        estadoRegistro: 'ACTIVO',
        rol: 'MODERADOR',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    },
    {
        _id: '661aad765a17e523f53fb226',
        nombre: 'Ronnie',
        email: 'mode3@correo.com',
        password: '$2a$10$p4DDvjHNrRVEk9SMLGZ6TOqYtZbVJZSU5oRU5wuzEs4EpawBjcS76',
        estadoRegistro: 'ACTIVO',
        rol: 'MODERADOR',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    }
]);

db.clientes.insertMany([
    {
        _id: '661aa51d50a424787193f372',
        nombre: 'Fiona Lucero',
        nickname: 'fiona',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [
            '661ade9b1911ac23d4bafbd5'
        ],
        recomendados: [
            '661aacb404561d72bdbf16f2'
        ],
        aprobacionesComentarios: [],
        email: 'fiona@gmail.com',
        password: '$2a$10$YAH8PV6Pa6UwhA4X3oIOp.D2wCck3mP15dsQbwM4PlSXDzfB3LV46',
        estadoRegistro: 'ACTIVO',
        rol: 'CLIENTE',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: '661aa62f1434fa40da4a039a',
        nombre: 'Ronnie James',
        nickname: 'Dio',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [
            '661aacb404561d72bdbf16f2'
        ],
        negocios: [
            '661aacb404561d72bdbf16f2',
            '661ae308bda88567695426c1'
        ],
        recomendados: [
            '661aacb404561d72bdbf16f2'
        ],
        aprobacionesComentarios: [],
        email: 'ronnie@gmail.com',
        password: '$2a$10$wehzH1H8mQmiLXeKOzQoBOFfm6tNnss6PM.2gFTw7DivBOarduK9i',
        estadoRegistro: 'ACTIVO',
        rol: 'CLIENTE',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: '661aa6b492e1d716362980a0',
        nombre: 'Sacha',
        nickname: 'chusacha',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [
            '661adf7fb017e172e90f9896',
            '661ae25e87e99b3d73ec998a'
        ],
        recomendados: [],
        aprobacionesComentarios: [],
        email: 'sacha@gmail.com',
        password: '$2a$10$C4t.dLokIa25.6h4Kz6lTuo434tHKdsPuiQ/Qh6p9bHxJy8NoGVbu',
        estadoRegistro: 'ACTIVO',
        rol: 'CLIENTE',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: '661aa710a04bed5af9438baa',
        nombre: 'Pika',
        nickname: 'pikiña',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [
            '661aacb404561d72bdbf16f2'
        ],
        aprobacionesComentarios: [],
        email: 'pika@gmail.com',
        password: '$2a$10$HlMCP68MXIIwQ0pkLPXGq.4z4HGtFLOeaNSvoeZQdWO0qRWO6BKqm',
        estadoRegistro: 'ACTIVO',
        rol: 'CLIENTE',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: '661aa76c33505c024afd0680',
        nombre: 'Elmo',
        nickname: 'elmocho',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [
            '661aacb404561d72bdbf16f2'
        ],
        aprobacionesComentarios: [],
        email: 'elmo@gmail.com',
        password: '$2a$10$dLC.LZIlpiLGqaPoI24ntu6l/5Xgen1MKuLIbvfH2Df.WefQt.Fxa',
        estadoRegistro: 'ACTIVO',
        rol: 'CLIENTE',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    }
])
db.negocios.insertMany([
    {
        _id: '661aacb404561d72bdbf16f2',
        codigoCliente: '661aa62f1434fa40da4a039a',
        nombre: 'La Perrada de Ronnie',
        ubicacion: {
            latitud: 135444770,
            longitud: 985862111
        },
        descripcion: 'Los mejores perros calientes de la ciudad',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS',
            'BAR'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '09:00:00 PM'
            }
        ],
        telefonos: [
            '3018967657',
            '3102647656'
        ],
        historialRevisiones: [
            {
                descripcion: '',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/04/13 11:03:00.000 AM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/13 12:12:57.000 PM',
                codigoModerador: '661aad765a17e523f53fb224',
                codigoNegocio: '661aacb404561d72bdbf16f2'
            }
        ],
        imagenes: [
            'cloudinary_1.com',
            'cloudinary_2.com'
        ],
        calificaciones: [
            'FOUR_STAR',
            'FIVE_STAR',
            'FOUR_STAR'
        ],
        recomendaciones: [
            1,
            1,
            1,
            1,

        ],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    },
    {
        _id: '661ade9b1911ac23d4bafbd5',
        codigoCliente: '661aa51d50a424787193f372',
        nombre: 'Peluqueria Fiona',
        ubicacion: {
            latitud: 145444770,
            longitud: 596862321
        },
        descripcion: 'La mejor atencion en su corte de cabello',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS',
            'BAR'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '09:00:00 PM'
            }
        ],
        telefonos: [
            '3018967657',
            '3102647656'
        ],
        historialRevisiones: [
            {
                descripcion: '',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/04/13 02:35:54.000 PM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: 'La ubicación propuesta coincide con un establecimiento ya vigente',
                estadoNegocio: 'RECHAZADO',
                fecha: '2024/04/13 02:57:56.000 PM',
                codigoModerador: '661aad765a17e523f53fb224',
                codigoNegocio: '661ade9b1911ac23d4bafbd5'
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/13 03:03:39.000 PM',
                codigoModerador: '661aad765a17e523f53fb224',
                codigoNegocio: '661ade9b1911ac23d4bafbd5'
            }
        ],
        imagenes: [
            'cloudinary_1.com',
            'cloudinary_2.com'
        ],
        calificaciones: [],
        recomendaciones: [],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    },
    {
        _id: '661adf7fb017e172e90f9896',
        codigoCliente: '661aa6b492e1d716362980a0',
        nombre: 'Supermercado la popular',
        ubicacion: {
            latitud: 140343570,
            longitud: 596862872
        },
        descripcion: 'Precios economicos y grandes ofertas',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS',
            'BAR'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '09:00:00 PM'
            }
        ],
        telefonos: [
            '3018967657',
            '3102647656'
        ],
        historialRevisiones: [
            {
                descripcion: '',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/04/13 02:39:43.000 PM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/13 03:06:21.000 PM',
                codigoModerado: '661aad765a17e523f53fb224',
                codigoNegocio: '661adf7fb017e172e90f9896'
            }
        ],
        imagenes: [
            'cloudinary_1.com',
            'cloudinary_2.com'
        ],
        calificaciones: [],
        recomendaciones: [],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    },
    {
        _id: '661ae25e87e99b3d73ec998a',
        codigoCliente: '661aa6b492e1d716362980a0',
        nombre: 'Supermercado la popular 2',
        ubicacion: {
            latitud: 282444770,
            longitud: 743862111
        },
        descripcion: 'Precios economicos y grandes ofertas',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS',
            'BAR'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '09:00:00 PM'
            }
        ],
        telefonos: [
            '3018967657',
            '3102647656'
        ],
        historialRevisiones: [
            {
                descripcion: '',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/04/13 02:51:58.000 PM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/13 03:09:24.000 PM',
                codigoModerador: '661aad765a17e523f53fb224',
                codigoNegocio: '661ae25e87e99b3d73ec998a'
            }
        ],
        imagenes: [
            'cloudinary_1.com',
            'cloudinary_2.com'
        ],
        calificaciones: [],
        recomendaciones: [],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    },
    {
        _id: '661ae308bda88567695426c1',
        codigoCliente: '661aa62f1434fa40da4a039a',
        nombre: 'La Segunda Perrada de Ronnie',
        ubicacion: {
            latitud: 575144770,
            longitud: 765865111
        },
        descripcion: 'Los mejores perros calientes y hamburguesas de la ciudad',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS',
            'BAR'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '09:00:00 PM'
            }
        ],
        telefonos: [
            '3018967657',
            '3102647656'
        ],
        historialRevisiones: [
            {
                descripcion: '',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/04/13 02:54:48.000 PM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/13 03:13:44.000 PM',
                codigoModerador: '661aad765a17e523f53fb224',
                codigoNegocio: '661ae308bda88567695426c1'
            }
        ],
        imagenes: [
            'cloudinary_1.com',
            'cloudinary_2.com'
        ],
        calificaciones: [],
        recomendaciones: [],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    }
]);

db.comentarios.insertMany([
    {
        _id: '661ae8b3ee37de6100ef8c5d',
        codigoCliente: '661aa51d50a424787193f372',
        codigoNegoci: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuest: '',
        fechaMensaje: '2024/04/13 03:18:56.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661ae90735a89a1e74bbcc7b',
        codigoCliente: '661aa6b492e1d716362980a0',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:20:21.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661ae97c0cb2fd031f74750c',
        codigoCliente: '661aa710a04bed5f9438baa',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:22:18.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661ae9f66434680bfa3b7e2e',
        codigoCliente: '661aa76c33505c024afd0680',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:24:18.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661aead48eb6862012a89348',
        codigoCliente: '661aa6b492e1d716362980a0',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:28:02.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    }
]);