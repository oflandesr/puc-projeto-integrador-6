# Use a Node.js base image
FROM node:20

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY frontend/package.json frontend/package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY frontend/ ./

# Build the React application
RUN npm run build

# Expose the application port
EXPOSE 3000

# Start the React application
CMD ["npm", "start"]
