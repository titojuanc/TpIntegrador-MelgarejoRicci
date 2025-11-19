import React, { useState } from 'react';
import { User, Sparkles } from 'lucide-react';
import Header from './components/Header';
import Footer from './components/Footer';
import StatsTable from './components/StatsTable';
import StarRating from './components/StarRating';
import PublicationCard from './components/PublicationCard';
import { MOCK_USER } from './constants';
import { generateReputationSummary } from './services/geminiService';

const App: React.FC = () => {
  const [aiSummary, setAiSummary] = useState<string | null>(null);
  const [loadingAi, setLoadingAi] = useState(false);

  const handleGenerateSummary = async () => {
    setLoadingAi(true);
    const summary = await generateReputationSummary(MOCK_USER.reviews, MOCK_USER.name);
    setAiSummary(summary);
    setLoadingAi(false);
  };

  return (
    <div className="min-h-screen bg-[#fff1f2] flex flex-col font-sans">
      <Header />

      <main className="flex-grow container mx-auto px-4 py-8 md:px-8 max-w-6xl">
        
        {/* Hero Section: Avatar & Name */}
        <div className="flex flex-col items-center mb-12">
          <div className="w-32 h-32 rounded-full border-2 border-[#1a1b41] flex items-center justify-center mb-4 bg-white shadow-sm">
             <User size={64} strokeWidth={1} className="text-[#1a1b41]" />
          </div>
          <h1 className="text-2xl md:text-3xl font-bold text-[#1a1b41] tracking-wide">
            {MOCK_USER.name}
          </h1>
          <p className="text-gray-500 text-sm mt-1">Usuario verificado</p>
          
          {/* AI Enhancement Feature */}
          {!aiSummary && !loadingAi && (
            <button 
              onClick={handleGenerateSummary}
              className="mt-4 flex items-center gap-2 text-xs bg-white border border-purple-200 text-purple-700 px-3 py-1 rounded-full shadow-sm hover:bg-purple-50 transition"
            >
              <Sparkles size={12} />
              Analizar reputación con IA
            </button>
          )}
          
          {loadingAi && (
            <p className="mt-4 text-xs text-purple-600 animate-pulse">Generando análisis...</p>
          )}

          {aiSummary && (
             <div className="mt-4 max-w-lg text-center bg-white/50 border border-purple-100 p-3 rounded-lg text-xs text-purple-900 italic">
               <Sparkles size={10} className="inline mr-1 text-purple-500"/>
               "{aiSummary}"
             </div>
          )}
        </div>

        {/* Main Grid Content */}
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 lg:gap-16">
          
          {/* LEFT COLUMN: Stats & Reviews (Span 5) */}
          <div className="lg:col-span-5 space-y-12">
            
            {/* Stats Block */}
            <section className="flex flex-col items-center lg:items-start">
              <StatsTable 
                level={MOCK_USER.level} 
                reputation={MOCK_USER.reputationPoints} 
                count={MOCK_USER.publicationCount} 
              />
            </section>

            {/* Reviews Block */}
            <section>
              <h2 className="text-xl font-bold text-[#1a1b41] mb-6">Calificaciones:</h2>
              <div className="grid grid-cols-2 gap-4">
                {MOCK_USER.reviews.map((review) => (
                  <div key={review.id} className="bg-white bg-opacity-40 border border-white p-4 rounded-lg shadow-sm flex flex-col justify-between min-h-[80px]">
                    <div className="mb-2">
                      <StarRating rating={review.rating} size={16} />
                    </div>
                    <p className="text-[10px] text-gray-400 text-right font-medium">
                      {review.date}
                    </p>
                  </div>
                ))}
              </div>
            </section>
          </div>

          {/* RIGHT COLUMN: Publications (Span 7) */}
          <div className="lg:col-span-7 border-l-0 lg:border-l border-pink-200 lg:pl-12">
            <h2 className="text-xl font-bold text-[#1a1b41] mb-6">Últimas publicaciones:</h2>
            
            <div className="space-y-6">
              {MOCK_USER.publications.map((pub) => (
                <PublicationCard key={pub.id} pub={pub} />
              ))}
            </div>
            
            <div className="mt-8 text-center">
              <button className="text-sm text-[#1a1b41] underline decoration-dotted hover:text-pink-600">
                Ver todas las publicaciones
              </button>
            </div>
          </div>

        </div>
      </main>

      <Footer />
    </div>
  );
};

export default App;