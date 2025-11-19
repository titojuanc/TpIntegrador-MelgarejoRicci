import { UserProfile, UserLevel } from './types';

export const MOCK_USER: UserProfile = {
  id: 'u1',
  name: 'Nombre de usuario',
  level: UserLevel.BRONZE,
  reputationPoints: 1250,
  publicationCount: 12,
  reviews: [
    {
      id: 'r1',
      userName: 'Usuario A',
      rating: 5,
      comment: 'Excelente vendedor, muy rápido.',
      date: '12/05/2024'
    },
    {
      id: 'r2',
      userName: 'Usuario B',
      rating: 4,
      comment: 'Buen producto pero tardó un poco.',
      date: '10/05/2024'
    },
    {
      id: 'r3',
      userName: 'Usuario C',
      rating: 5,
      comment: 'Todo perfecto, recomendable.',
      date: '08/05/2024'
    },
    {
      id: 'r4',
      userName: 'Usuario D',
      rating: 5,
      comment: 'Muy amable, gracias.',
      date: '01/05/2024'
    }
  ],
  publications: [
    {
      id: 'p1',
      title: 'Nombre Producto',
      category: 'Categoría',
      price: 10000,
      description: 'Descripción breve del producto en venta. Excelente estado.',
      imageUrl: 'https://picsum.photos/300/300?random=1',
      promoted: true
    },
    {
      id: 'p2',
      title: 'Nombre Producto',
      category: 'Categoría',
      price: 10000,
      description: 'Descripción breve del producto en venta. Poco uso.',
      imageUrl: 'https://picsum.photos/300/300?random=2',
      promoted: true
    },
    {
      id: 'p3',
      title: 'Nombre Producto',
      category: 'Categoría',
      price: 10000,
      description: 'Descripción breve del producto en venta.',
      imageUrl: 'https://picsum.photos/300/300?random=3',
      promoted: true
    }
  ]
};