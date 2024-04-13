db = connect('mongodb://root:example@localhost:27017/proyecto-test?authSource=admin');
db.clientes.insertMany([
    {
        _id: {
            $oid: '660842f2e1f50b64a6376e3c'
        },
        nombre: 'sacha',
        nickname: 'sacha',
        ciudad: 'RISARALDA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [
            '6608438bfd6d342c8005bdc8'
        ],
        negocios: [
            '6608438bfd6d342c8005bdc8',
            '6608442e31eff35db34b4b8a'
        ],
        recomendados: [
            '6608438bfd6d342c8005bdc8'
        ],
        aprobacionesComentarios: [
            '660eb8c49bcd74720e2df999'
        ],
        email: 'leoromero141@gmail.com',
        password: '$2a$10$1zX.0gLJsMo2bw0M.2TkkeTvKzsADXaA0wk6aciPd5hglr.C/e3lu',
        estadoRegistro: 'ACTIVO',
        rol: 'USUARIO',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: {
            $oid: '6608622d7a6bf86424f727d3'
        },
        nombre: 'Ronnie James Dio',
        nickname: 'Ronnie',
        ciudad: 'ARMENIA',
        fotoPerfil: 'foto1.jpg',
        favoritos: [],
        negocios: [
            '6608abf0548f03646c38dbd8',
            '660dd5416990af4a40637949',
            '660ee368ba2cce724783399f',
            '660ee420767f881e7797e463',
            '660f7b100dfb28723e03c232',
            '6611f8060d65450fe2d86d4c'
        ],
        recomendados: [],
        aprobacionesComentarios: [
            '66086e5a7d8dcb33817cd67f',
            '660eb8c49bcd74720e2df999'
        ],
        email: 'ronnie@gmail.com',
        password: '$2a$10$Hl4CTbbRoYilUXtbyCctn.nflxo0xxoLu2wl9gYkT1y/IO0SE3q6a',
        estadoRegistro: 'ACTIVO',
        rol: 'USUARIO',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    },
    {
        _id: {
            $oid: '660862be705e055490c3753c'
        },
        nombre: 'Fiona Lucero',
        nickname: 'fiona',
        'ciudad': 'ARMENIA',
        'fotoPerfil': 'foto1.jpg',
        'favoritos': [],
        'negocios': [],
        'recomendados': [],
        'aprobacionesComentarios': [
            '660eb8c49bcd74720e2df999'
        ],
        'email': 'fiona@gmail.com',
        'password': '$2a$10$N.iO7yzLweENpQxH9RAgluTX7Pj9o3kBCkZ4RpeSDiBAO3ofUL4SK',
        'estadoRegistro': 'ACTIVO',
        'rol': 'USUARIO',
        '_class': 'co.edu.uniquindio.proyecto.modelo.documentos.Cliente'
    }
]);
db.negocios.insertMany([
    {
        _id: {
            $oid: '6608438bfd6d342c8005bdc8'
        },
        codigoCliente: '660842f2e1f50b64a6376e3c',
        nombre: 'La Segunda Perrada de Sacha',
        ubicacion: {
            latitud: 693453780,
            longitud: 957654624
        },
        descripcion: 'Los mejores perros calientes y hamburguesas de la ciudad',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS'
        ],
        horarios: [
            {
                dia: 'MONDAY',
                horaInicio: '10:00:00 AM',
                horaFin: '09:00:00 PM'
            }
        ],
        telefonos: [
            '3013967627',
            '3152647156'
        ],
        historialRevisiones: [
            {
                descripcion: 'La ubicación propuesta coincide con un establecimiento presente',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/03/29 03:41:20.000 PM',
                codigoModerador: '1',
                codigoNegocio: '6608438bfd6d342c8005bdc8'
            },
            {
                descripcion: 'La ubicación propuesta coincide con un establecimiento presente',
                estadoNegocio: 'RECHAZADO',
                fecha: '2024/03/30 05:41:20.000 PM',
                codigoModerador: '1',
                codigoNegocio: '6608438bfd6d342c8005bdc8'
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/01 12:15:01.000 PM',
                codigoModerador: '1',
                codigoNegocio: '6608438bfd6d342c8005bdc8'
            }
        ],
        imagenes: [
            'cloudinary_1.com',
            'cloudinary_2.com'
        ],
        calificaciones: [
            'THREE_STAR',
            'FIVE_STAR',
            'FOUR_STAR',
            'FOUR_STAR'
        ],
        recomendaciones: [],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Negocio'
    },
    {
        _id: {
            $oid: '6608442e31eff35db34b4b8a'
        },
        codigoCliente: '660842f2e1f50b64a6376e3c',
        nombre: 'La Primera Perrada de Sacha',
        ubicacion: {
            latitud: 293454780,
            longitud: 657654324
        },
        descripcion: 'Los mejores perros calientes de la ciudad',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS'
        ],
        horarios: [
            {
                'dia': 'WEDNESDAY',
                'horaInicio': '10:00:00',
                'horaFin': '21:00:00'
            }
        ],
        telefonos: [
            '3018967657',
            '3102647656'
        ],
        historialRevisiones: [
            {
                descripcion: 'La ubicación propuesta coincide con un establecimiento presente',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/03/31 04:12:15.000 PM',
                codigoModerador: 'Default',
                codigoNegocio: ''
            },
            {
                descripcion: 'La ubicación propuesta coincide con un establecimiento presente',
                estadoNegocio: 'APROBADO',
                fecha: '2024/03/31 05:05:45.000 PM',
                codigoModerador: '1',
                codigoNegocio: '6608442e31eff35db34b4b8a'
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
        _id: {
            $oid: '6608abf0548f03646c38dbd8'
        },
        codigoCliente: '6608622d7a6bf86424f727d3',
        nombre: 'La Primera Perrada de Ronnie',
        ubicacion: {
            latitud: 453454780,
            longitud: 217654324
        },
        descripcion: 'Los mejores perros calientes de la ciudad',
        stadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS'
        ],
        horarios: [
            {
                dia: 'Thursday',
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
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'PENDIENTE',
                fecha: '2024/04/04 11:24:39.000 PM',
                codigoModerador: 'Default',
                codigoNegocio: ''
            },
            {
                descripcion: 'Su negocio cumple con las normas de la aplicación',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/05 10:14:25.000 PM',
                codigoModerador: '1',
                codigoNegocio: '6608abf0548f03646c38dbd8'
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
        _id: {
            $oid: '660dd5416990af4a40637949'
        },
        codigoCliente: '6608622d7a6bf86424f727d3',
        nombre: 'La Tercera Perrada de Ronnie',
        ubicacion: {
            latitud: 452254780,
            longitud: 217884324
        },
        descripcion: 'Los mejores perros calientes de la ciudad',
        estadoNegocio: 'APROBADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS'
        ],
        horarios: [
            {
                dia: 'WEDNESDAY',
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
                fecha: '2024/04/03 05:16:20.000 PM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: '',
                estadoNegocio: 'APROBADO',
                fecha: '2024/04/04 05:20:11.000 PM',
                codigoModerador: '1',
                codigoNegocio: '660dd5416990af4a40637949'
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
        _id: {
            $oid: '660ee420767f881e7797e463'
        },
        codigoCliente: '6608622d7a6bf86424f727d3',
        nombre: 'La Cuarta Perrada de Ronnie',
        ubicacion: {
            latitud: 296544780,
            longitud: 657564324
        },
        descripcion: 'Los mejores perros calientes de la ciudad',
        estadoNegocio: 'ELIMINADO',
        tipoNegocios: [
            'COMIDAS_RAPIDAS'
        ],
        horarios: [
            {
                dia: 'SUNDAY',
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
                fecha: '2024/04/04 10:32:05.000 AM',
                codigoModerador: 'default',
                codigoNegocio: ''
            },
            {
                descripcion: '',
                estadoNegocio: 'RECHAZADO',
                fecha: '2024/04/04 12:30:18.000 PM',
                codigoModerador: '1',
                codigoNegocio: '660ee420767f881e7797e463'
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
])
db.comentarios.insertMany([
    {
        _id: {
            $oid: '66086e5a7d8dcb33817cd67f'
        },
        codigoCliente: '6608622d7a6bf86424f727d3',
        codigoNegocio: '6611f8060d65450fe2d86d4c',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/03/30 02:56:12.000 PM',
        fechaRespuesta: '',
        meGusta: [
            1
        ],
        _clas: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: {
            $oid: '660eb8c49bcd74720e2df999'
        },
        codigoCliente: '660842f2e1f50b64a6376e3c',
        codigoNegocio: '6608438bfd6d342c8005bdc8',
        mensaje: 'este es el mensaje',
        respuesta: '',
        fechaMensaje: '2024/04/04 02:27:25.000 PM',
        fechaRespuesta: '',
        meGusta: [
            1,
            1,
            1
        ],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    },
    {
        _id: {
            $oid: '66135c174d13ae048cf2d67e'
        },
        codigoCliente: '6608622d7a6bf86424f727d3',
        codigoNegocio: '6611f8060d65450fe2d86d4c',
        mensaje: 'ese es el mensaje',
        respuesta: 'este es la respuesta',
        fechaMensaje: '2024/04/07 09:53:11.000 PM',
        fechaRespuesta: '2024/04/07 10:15:10.000 PM',
        meGusta: [],
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Comentario'
    }
]);
db.moderadores.insertMany([
    {
        _id: {
            $oid: '6618bece184b4b36653f9ad8'
        },
        nombre: 'David',
        email: 'mode1@correo.com',
        password: '$2a$10$Lpc7Yym7PmRdFPxnco0S.e54DodK4WejyGWXXjKzlF5kSQ69hcyY2',
        estadoRegistro: 'ACTIVO',
        rol: 'ADMINISTRADOR',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    },
    {
        _id: {
            $oid: '6618bece184b4b36653f9ad9'
        },
        nombre: 'Leonardo',
        email: 'mode2@correo.com',
        password: '$2a$10$p6zCL1wHJasEF6HpdAbafeokL66yn2xLzzxphrkP74xVO9CBLv1ny',
        estadoRegistro: 'ACTIVO',
        rol: 'ADMINISTRADOR',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    },
    {
        _id: {
            $oid: '6618bece184b4b36653f9ada'
        },
        nombre: 'Ronnie',
        email: 'mode3@correo.com',
        password: '$2a$10$3eSfhbDgV35fS2rkSRuCjuejRuJMCQ36/hFjYahY/9ZA45drson6K',
        estadoRegistro: 'ACTIVO',
        rol: 'ADMINISTRADOR',
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Moderador'
    }
]);
