db = connect('mongodb://root:example@localhost:27018/proyecto-test?authSource=admin');

db.moderadores.insertMany([
    {
        _id: ObjectId('661c478c84bcf729883e3c2e'),
        nombre: 'David',
        email: 'mode1@correo.com',
        password: '$2a$10$F9xgA/6V.rQ1s4SCCoioUem0F/nwzWdrdwphkjA27e/jDB2QfjCxS',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c478b84bcf729883e3c2d'),
            rol: 'MODERADOR',
            listaPermisos: [
                'APROBAR',
                'COMENTAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    },
    {
        _id: ObjectId('661c478c84bcf729883e3c2f'),
        nombre: 'Leonardo',
        email: 'mode2@correo.com',
        password: '$2a$10$v88gxFutjqwv734h9lbkeu80Oqzo8tQp/OicmE7XN.oA6B9C9QIqe',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c478b84bcf729883e3c2d'),
            rol: 'MODERADOR',
            listaPermisos: [
                'APROBAR',
                'COMENTAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    },
    {
        _id: ObjectId('661c478c84bcf729883e3c30'),
        nombre: 'Ronnie',
        email: 'mode3@correo.com',
        password: '$2a$10$.IbF9XqeAGT99OXtt/0K5.UTxrsrwpKTlxuabt9drTrsIxOODBy5i',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c478b84bcf729883e3c2d'),
            rol: 'MODERADOR',
            listaPermisos: [
                'APROBAR',
                'COMENTAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    }
]);

db.clientes.insertMany([
    {
        _id: ObjectId('661c48abd36eeb64ed610953'),
        nombre: 'Maria Cano',
        nickname: 'mary',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [],
        aprobacionesComentarios: [],
        email: 'mariacano@gmail.com',
        password: '$2a$10$.8i1NP9NxTnQUreHinnOc.0nfOnTJIxz.ZtRZcAFgA7czWUKvy386',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c48aad36eeb64ed610952'),
            rol: 'CLIENTE',
            listaPermisos: [
                'CREAR',
                'CALIFICAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: ObjectId('661c4a4289852b27687d80a7'),
        nombre: 'Roberto Lopez',
        nickname: 'beto',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [],
        aprobacionesComentarios: [],
        email: 'robertopez@gmail.com',
        password: '$2a$10$YCOvcXA0Gj2ocwGWbA/t2O.W3WBqRAQW/hjvzarx7IdMS/0f9xJ.y',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c4a4289852b27687d80a6'),
            rol: 'CLIENTE',
            listaPermisos: [
                'CREAR',
                'CALIFICAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: ObjectId('661c4aaa02acb36e691c9c17'),
        nombre: 'Cecilia Muñoz',
        nickname: 'cecy',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [],
        aprobacionesComentarios: [],
        email: 'ceciliañoz@gmail.com',
        password: '$2a$10$KPiqRVr2jOASWl4MgynHGerIsQigtva7M77cUYsxZtM1XA7vywdXy',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c4aaa02acb36e691c9c16'),
            rol: 'CLIENTE',
            listaPermisos: [
                'CREAR',
                'CALIFICAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: ObjectId('661c4b0ea2ece971f83f5a7b'),
        nombre: 'Juan Valdez',
        nickname: 'juancho',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [],
        aprobacionesComentarios: [],
        email: 'juanvaldez@gmail.com',
        password: '$2a$10$vt3qxTreVZl7VMdnhh45yO0YFH3x6IFJqeYAbNI/U667gVHXidCt.',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c4b0ea2ece971f83f5a7a'),
            rol: 'CLIENTE',
            listaPermisos: [
                'CREAR',
                'CALIFICAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: ObjectId('661c4b6c03dc96547afaca75'),
        nombre: 'Pedro Perez',
        nickname: 'pepe',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [],
        recomendados: [],
        aprobacionesComentarios: [],
        email: 'pedrope@gmail.com',
        password: '$2a$10$slwri/gpINlOTBU1EnjwbOM9qBYrEhAiSCeMSPrIgPkF1OuQTj20K',
        estadoRegistro: 'ACTIVO',
        rol: {
            _id: ObjectId('661c4b6b03dc96547afaca74'),
            rol: 'CLIENTE',
            listaPermisos: [
                'CREAR',
                'CALIFICAR'
            ]
        },
        isEnabled: true,
        accountNoExpired: true,
        accountNoLocked: true,
        credentialNoExpired: true,
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    }
])
db.negocios.insertMany([
    {
        _id: '661aacb404561d72bdbf16f2',
        codigoCliente: '661c48abd36eeb64ed610953',
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
                horaInicio: '10:00:00',
                horaFin: '23:40:00'
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
                codigoModerador: '661c478c84bcf729883e3c2e',
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
            1
        ],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    },
    {
        _id: '661ade9b1911ac23d4bafbd5',
        codigoCliente: '661c48abd36eeb64ed610953',
        nombre: 'Peluqueria Fiona',
        ubicacion: {
            latitud: 145444770,
            longitud: 596862321
        },
        descripcion: 'La mejor atencion en su corte de cabello',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'PELUQUERIA'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00',
                horaFin: '20:00:00'
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
                codigoModerador: '661c478c84bcf729883e3c2e',
                codigoNegocio: '661ade9b1911ac23d4bafbd5'
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/13 03:03:39.000 PM',
                codigoModerador: '661c478c84bcf729883e3c2e',
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
        codigoCliente: '661c48abd36eeb64ed610953',
        nombre: 'Supermercado la popular',
        ubicacion: {
            latitud: 140343570,
            longitud: 596862872
        },
        descripcion: 'Precios economicos y grandes ofertas',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'SUPERMERCADO'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00',
                horaFin: '18:00:00'
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
                codigoModerado: '661c478c84bcf729883e3c2e',
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
        codigoCliente: '661c48abd36eeb64ed610953',
        nombre: 'Supermercado la popular 2',
        ubicacion: {
            latitud: 282444770,
            longitud: 743862111
        },
        descripcion: 'Precios economicos y grandes ofertas',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'SUPERMERCADO'
        ],
        horarios: [
            {
                dia: 'SATURDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '18:00:00 PM'
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
                codigoModerador: '661c478c84bcf729883e3c2e',
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
        codigoCliente: '661c48abd36eeb64ed610953',
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
                horaInicio: '10:00:00',
                horaFin: '23:40:00'
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
                codigoModerador: '661c478c84bcf729883e3c2e',
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
        codigoCliente: '661c48abd36eeb64ed610953',
        codigoNegoci: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuest: '',
        fechaMensaje: '2024/04/13 03:18:56.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661ae90735a89a1e74bbcc7b',
        codigoCliente: '661c48abd36eeb64ed610953',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:20:21.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661ae97c0cb2fd031f74750c',
        codigoCliente: '661c48abd36eeb64ed610953',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:22:18.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661ae9f66434680bfa3b7e2e',
        codigoCliente: '661c48abd36eeb64ed610953',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:24:18.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: '661aead48eb6862012a89348',
        codigoCliente: '661c48abd36eeb64ed610953',
        codigoNegocio: '661aacb404561d72bdbf16f2',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/13 03:28:02.000 PM',
        fechaRespuesta: '',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    }
]);
db.roles.insertMany([

]);