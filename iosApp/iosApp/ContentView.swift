import SwiftUI
import shared

struct ContentView: View {
	//let greet = Greeting().greet()
    private let cache = Cache()
    @State private var value: String = ""

	var body: some View {
		//Text(greet)
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
            
        }
        .padding(20)
        .onAppear() {
            self.value = cache.getUsername()
        }
	}
}



struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
