import React from 'react';
import { CreditCard } from 'lucide-react';

const Footer: React.FC = () => {
  return (
    <footer className="w-full bg-[#1a1b41] text-white mt-12">
      <div className="container mx-auto px-6 py-12 flex flex-col md:flex-row justify-between items-start">
        
        {/* Left CTA */}
        <div className="mb-8 md:mb-0">
          <h3 className="text-lg uppercase tracking-widest text-pink-100 mb-2">Únete para más</h3>
          <button className="text-2xl font-serif italic border-b border-white pb-1 hover:text-pink-200 transition">
            Registrate
          </button>
        </div>

        {/* Right Links */}
        <div className="text-right text-xs md:text-sm text-gray-300 space-y-2">
          <p className="hover:text-white cursor-pointer">TERMINOS Y CONDICIONES</p>
          <p className="hover:text-white cursor-pointer">CONTACTANOS</p>
          <p className="hover:text-white cursor-pointer">POLITICA ENVIO</p>
          
          <div className="flex justify-end gap-2 mt-4 pt-4">
             {/* Payment Icons Simulation */}
             <div className="w-8 h-5 bg-blue-600 rounded"></div>
             <div className="w-8 h-5 bg-red-500 rounded"></div>
             <div className="w-8 h-5 bg-orange-500 rounded"></div>
             <div className="w-8 h-5 bg-blue-400 rounded"></div>
          </div>
          <p className="text-[10px] mt-4 text-gray-500">
             © 2025 • MercadoTemplate | Tecnología de Shopify | Designed by UI/UX Experts
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;