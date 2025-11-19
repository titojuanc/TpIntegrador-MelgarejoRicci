import React from 'react';
import { Publication } from '../types';
import { Tag } from 'lucide-react';

interface PublicationCardProps {
  pub: Publication;
}

const PublicationCard: React.FC<PublicationCardProps> = ({ pub }) => {
  return (
    <div className="bg-white bg-opacity-60 border border-white shadow-sm rounded-lg overflow-hidden flex h-40 md:h-48 transition hover:shadow-md">
      {/* Image Section */}
      <div className="w-1/3 relative bg-gray-200">
        <img 
          src={pub.imageUrl} 
          alt={pub.title} 
          className="w-full h-full object-cover opacity-90"
        />
        {/* Overlay gradient to match mockup feel */}
        <div className="absolute inset-0 bg-gradient-to-r from-gray-900/10 to-transparent pointer-events-none" />
      </div>

      {/* Content Section */}
      <div className="w-2/3 p-4 flex flex-col justify-between relative">
        <div>
          {pub.promoted && (
             <div className="absolute top-4 right-4 bg-green-500 text-white text-[10px] font-bold px-2 py-0.5 rounded uppercase">
               Promocionado
             </div>
          )}
          <p className="text-xs text-gray-500 mb-1">{pub.category}</p>
          <h3 className="font-bold text-[#1a1b41] leading-tight mb-1">{pub.title}</h3>
          <p className="text-xl font-bold text-[#1a1b41] mb-2">
            ${pub.price.toLocaleString('es-CL')}
          </p>
          <p className="text-xs text-gray-600 line-clamp-2 leading-relaxed">
            {pub.description}
          </p>
        </div>
        
        <div className="text-right">
           <span className="text-[10px] text-gray-400">Ver detalle &rarr;</span>
        </div>
      </div>
    </div>
  );
};

export default PublicationCard;