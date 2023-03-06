import SwiftUI
import shared

struct ContentView: View {
    private let cache = Cache()
    private let apiBridge = ApiBridge()
    
    @State private var value: String = ""
    @State private var movies: Results? = nil
    @State private var showingSheet = false

	var body: some View {
        VStack {
            Text("Username: \(value)")
            
            TextField("Username", text: $value)
            Button(action: {
                cache.saveUserName(value: value)
            }, label: {
                Text("Save")
            })
            
            Button(action: {
                cache.removeAllCache()
            }, label: {
                Text("Clear Cache")
            })
        
            Button(action: {
                apiBridge.getMovies(
                    successProcess: {result in
                        self.movies = result
                    },
                    errorProcess: {error in
                        self.movies = nil
                    },
                    completionHandler: {_ in}
                )
            }, label: {
                Text("Get Movies")
            })
            
        }
        .padding(20)
        .onAppear() {
            self.value = cache.getUsername()
        }
        .onChange(of: self.movies, perform: { newValue in
            if (newValue != nil) {
                self.showingSheet.toggle()
            }
        })
        .sheet(isPresented: $showingSheet) {
            ScrollView {
                ForEach(movies!.results, id: \.self) { movie in
                    Text("Title \(movie.title)")
                    Text("Original Language \(movie.original_language)")
                    Text("Id \(movie.id)")
                    Text("-------------")
                }
            }
        }
	}
}



struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
