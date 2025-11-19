import { GoogleGenAI } from "@google/genai";
import { Review } from "../types";

export const generateReputationSummary = async (reviews: Review[], userName: string): Promise<string> => {
  if (!process.env.API_KEY) {
    console.warn("API Key not found");
    return "API Key is missing. Cannot generate summary.";
  }

  try {
    const ai = new GoogleGenAI({ apiKey: process.env.API_KEY });
    
    const reviewText = reviews.map(r => `- ${r.userName} (${r.rating}/5): ${r.comment}`).join('\n');
    
    const prompt = `
      You are an AI assistant for a marketplace profile page.
      Analyze the following reviews for user "${userName}".
      Provide a short, encouraging 2-sentence summary of their reputation based on these reviews.
      Highlight their key strengths.
      
      Reviews:
      ${reviewText}
    `;

    const response = await ai.models.generateContent({
      model: 'gemini-2.5-flash',
      contents: prompt,
    });

    return response.text || "No summary available.";
  } catch (error) {
    console.error("Error generating summary:", error);
    return "Unable to generate summary at this time.";
  }
};